package com.pocketdev.ui.editor

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout
import com.pocketdev.R
import com.pocketdev.data.local.database.AppDatabase
import com.pocketdev.data.local.entity.ProjectEntity
import com.pocketdev.data.local.preferences.SettingsPreferences
import com.pocketdev.data.repository.AiRepository
import com.pocketdev.execution.ErrorType
import com.pocketdev.execution.ExecutionManager
import com.pocketdev.execution.ExecutionResult
import com.pocketdev.execution.engine.HtmlExecutionEngine
import com.pocketdev.model.AiModel
import com.pocketdev.model.ProgrammingLanguage
import io.github.rosemoe.sora.langs.textmate.TextMateLanguage
import io.github.rosemoe.sora.widget.CodeEditor
import io.github.rosemoe.sora.widget.component.Magnifier
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest

class EditorActivity : AppCompatActivity() {

    private lateinit var codeEditor: CodeEditor
    private lateinit var consoleOutput: TextView
    private lateinit var webView: WebView
    private lateinit var tabLayout: TabLayout
    private lateinit var executionProgress: ProgressBar
    private lateinit var runButton: FloatingActionButton
    
    private var projectId: Long = -1
    private var currentProject: ProjectEntity? = null
    private var autoSaveJob: Job? = null
    
    private lateinit var settingsPreferences: SettingsPreferences
    private val executionManager = ExecutionManager()
    private val aiRepository = AiRepository()
    
    companion object {
        const val EXTRA_PROJECT_ID = "project_id"
        private const val AUTO_SAVE_INTERVAL_MS = 30000L // 30 seconds
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)
        
        settingsPreferences = SettingsPreferences(this)
        
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Editor"
        }
        
        projectId = intent.getLongExtra(EXTRA_PROJECT_ID, -1)
        
        initViews()
        setupCodeEditor()
        setupWebView()
        setupTabLayout()
        
        if (projectId != -1L) {
            loadProject(projectId)
        }
        
        startAutoSave()
    }
    
    private fun initViews() {
        codeEditor = findViewById(R.id.code_editor)
        consoleOutput = findViewById(R.id.console_output)
        webView = findViewById(R.id.web_view)
        tabLayout = findViewById(R.id.tab_layout)
        executionProgress = findViewById(R.id.execution_progress)
        runButton = findViewById(R.id.fab_run)
        
        runButton.setOnClickListener {
            runCode()
        }
    }
    
    @SuppressLint("SetTextI18n")
    private fun setupCodeEditor() {
        // Configure editor appearance
        codeEditor.apply {
            // Line numbers
            isLineNumberEnabled = settingsPreferences.isLineNumbersEnabled()
            
            // Font size
            setTextSize(settingsPreferences.getFontSizeSp())
            
            // Tab size
            tabWidth = settingsPreferences.getTabSize().spaces
            
            // Enable magnifier
            getComponent(Magnifier::class.java).apply {
                isEnabled = true
            }
            
            // Color scheme (dark theme default)
            colorScheme = EditorColorScheme(true)
        }
    }
    
    private fun setupLanguage(language: ProgrammingLanguage) {
        val textMateLanguage = when (language) {
            ProgrammingLanguage.PYTHON -> TextMateLanguage.create("source.python", true)
            ProgrammingLanguage.JAVASCRIPT -> TextMateLanguage.create("source.js", true)
            ProgrammingLanguage.HTML -> TextMateLanguage.create("text.html.basic", true)
            ProgrammingLanguage.CSS -> TextMateLanguage.create("source.css", true)
            ProgrammingLanguage.JAVA -> TextMateLanguage.create("source.java", true)
            ProgrammingLanguage.CPP -> TextMateLanguage.create("source.cpp", true)
            ProgrammingLanguage.KOTLIN -> TextMateLanguage.create("source.kotlin", true)
            ProgrammingLanguage.JSON -> TextMateLanguage.create("source.json", true)
        }
        
        codeEditor.setEditorLanguage(textMateLanguage)
    }
    
    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        webView.apply {
            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                loadWithOverviewMode = true
                useWideViewPort = true
            }
            webChromeClient = WebChromeClient()
            webViewClient = WebViewClient()
        }
    }
    
    private fun setupTabLayout() {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> showEditor()
                    1 -> showConsole()
                    2 -> showPreview()
                }
            }
            
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }
    
    private fun showEditor() {
        codeEditor.visibility = View.VISIBLE
        consoleOutput.visibility = View.GONE
        webView.visibility = View.GONE
    }
    
    private fun showConsole() {
        codeEditor.visibility = View.GONE
        consoleOutput.visibility = View.VISIBLE
        webView.visibility = View.GONE
    }
    
    private fun showPreview() {
        codeEditor.visibility = View.GONE
        consoleOutput.visibility = View.GONE
        webView.visibility = View.VISIBLE
    }
    
    private fun loadProject(id: Long) {
        lifecycleScope.launch {
            val project = AppDatabase.getDatabase(this@EditorActivity)
                .projectDao()
                .getProjectById(id)
            
            project?.let {
                currentProject = it
                supportActionBar?.title = it.name
                
                setupLanguage(it.language)
                codeEditor.setText(it.code)
                
                // Update run button based on language
                if (!it.language.isExecutable) {
                    runButton.isVisible = false
                }
            }
        }
    }
    
    private fun runCode() {
        val code = codeEditor.text.toString()
        val language = currentProject?.language ?: return
        
        if (!executionManager.canExecute(language)) {
            Toast.makeText(this, "This language cannot be executed", Toast.LENGTH_SHORT).show()
            return
        }
        
        // Switch to console tab for Python/JS, preview for HTML
        when (language) {
            ProgrammingLanguage.PYTHON, ProgrammingLanguage.JAVASCRIPT -> {
                tabLayout.getTabAt(1)?.select()
            }
            ProgrammingLanguage.HTML -> {
                tabLayout.getTabAt(2)?.select()
            }
            else -> {}
        }
        
        executionProgress.isVisible = true
        consoleOutput.text = ""
        
        lifecycleScope.launch {
            executionManager.execute(code, language).collectLatest { result ->
                when (result) {
                    is ExecutionResult.Output -> {
                        appendToConsole(result.text, result.type)
                    }
                    is ExecutionResult.Error -> {
                        appendToConsole(result.message, isError = true)
                        executionProgress.isVisible = false
                    }
                    is ExecutionResult.Success -> {
                        appendToConsole("\nExecution completed in ${result.executionTimeMs}ms")
                        executionProgress.isVisible = false
                    }
                    is ExecutionResult.HtmlOutput -> {
                        webView.loadDataWithBaseURL(null, result.htmlContent, "text/html", "UTF-8", null)
                        executionProgress.isVisible = false
                    }
                    is ExecutionResult.Running -> {
                        // Do nothing
                    }
                    is ExecutionResult.Cancelled -> {
                        appendToConsole("\nExecution cancelled")
                        executionProgress.isVisible = false
                    }
                }
            }
        }
    }
    
    private fun appendToConsole(text: String, type: com.pocketdev.execution.OutputType = com.pocketdev.execution.OutputType.STDOUT, isError: Boolean = false) {
        val coloredText = when {
            isError -> "<font color='#F44336'>$text</font>"
            type == com.pocketdev.execution.OutputType.LOG -> "<font color='#2196F3'>$text</font>"
            type == com.pocketdev.execution.OutputType.STDERR -> "<font color='#FF9800'>$text</font>"
            else -> text
        }
        
        consoleOutput.append(android.text.Html.fromHtml(coloredText, android.text.Html.FROM_HTML_MODE_COMPACT))
        consoleOutput.append("\n")
    }
    
    private fun startAutoSave() {
        if (!settingsPreferences.isAutoSaveEnabled()) return
        
        autoSaveJob = lifecycleScope.launch {
            while (isActive) {
                delay(AUTO_SAVE_INTERVAL_MS)
                saveProject()
            }
        }
    }
    
    private fun saveProject() {
        currentProject?.let { project ->
            val updatedCode = codeEditor.text.toString()
            lifecycleScope.launch {
                AppDatabase.getDatabase(this@EditorActivity)
                    .projectDao()
                    .updateProjectCode(project.id, updatedCode)
            }
        }
    }
    
    // AI Features
    private fun showAiFeatures() {
        val options = arrayOf("Fix Bug", "Explain Code", "Improve Code")
        
        MaterialAlertDialogBuilder(this)
            .setTitle("AI Assistant")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> fixBugWithAi()
                    1 -> explainCodeWithAi()
                    2 -> improveCodeWithAi()
                }
            }
            .show()
    }
    
    private fun fixBugWithAi() {
        val apiKey = settingsPreferences.getApiKey()
        if (apiKey.isNullOrBlank()) {
            showApiKeyRequiredDialog()
            return
        }
        
        val code = codeEditor.text.toString()
        val language = currentProject?.language ?: return
        
        showAiLoadingDialog("Analyzing code for bugs...")
        
        lifecycleScope.launch {
            val result = aiRepository.fixBug(code, language, apiKey, settingsPreferences.getAiModel())
            dismissAiLoadingDialog()
            
            when (result) {
                is com.pocketdev.data.remote.GroqResult.Success -> {
                    showAiResultDialog("Bug Fix", result.data, canApply = true)
                }
                is com.pocketdev.data.remote.GroqResult.Error -> {
                    Toast.makeText(this@EditorActivity, result.message, Toast.LENGTH_LONG).show()
                }
                com.pocketdev.data.remote.GroqResult.NetworkError -> {
                    Toast.makeText(this@EditorActivity, "No internet connection", Toast.LENGTH_LONG).show()
                }
                com.pocketdev.data.remote.GroqResult.RateLimitError -> {
                    Toast.makeText(this@EditorActivity, "Rate limit exceeded. Please try again later.", Toast.LENGTH_LONG).show()
                }
                com.pocketdev.data.remote.GroqResult.InvalidKeyError -> {
                    Toast.makeText(this@EditorActivity, "Invalid API key", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    
    private fun explainCodeWithAi() {
        val apiKey = settingsPreferences.getApiKey()
        if (apiKey.isNullOrBlank()) {
            showApiKeyRequiredDialog()
            return
        }
        
        val code = codeEditor.text.toString()
        val language = currentProject?.language ?: return
        
        showAiLoadingDialog("Explaining code...")
        
        lifecycleScope.launch {
            val result = aiRepository.explainCode(code, language, apiKey, settingsPreferences.getAiModel())
            dismissAiLoadingDialog()
            
            when (result) {
                is com.pocketdev.data.remote.GroqResult.Success -> {
                    showAiResultDialog("Code Explanation", result.data, canApply = false)
                }
                else -> handleAiError(result)
            }
        }
    }
    
    private fun improveCodeWithAi() {
        val apiKey = settingsPreferences.getApiKey()
        if (apiKey.isNullOrBlank()) {
            showApiKeyRequiredDialog()
            return
        }
        
        val code = codeEditor.text.toString()
        val language = currentProject?.language ?: return
        
        showAiLoadingDialog("Analyzing code for improvements...")
        
        lifecycleScope.launch {
            val result = aiRepository.improveCode(code, language, apiKey, settingsPreferences.getAiModel())
            dismissAiLoadingDialog()
            
            when (result) {
                is com.pocketdev.data.remote.GroqResult.Success -> {
                    showAiResultDialog("Code Improvements", result.data, canApply = true)
                }
                else -> handleAiError(result)
            }
        }
    }
    
    private var aiLoadingDialog: AlertDialog? = null
    
    private fun showAiLoadingDialog(message: String) {
        aiLoadingDialog = MaterialAlertDialogBuilder(this)
            .setView(R.layout.dialog_ai_loading)
            .setCancelable(false)
            .show()
    }
    
    private fun dismissAiLoadingDialog() {
        aiLoadingDialog?.dismiss()
        aiLoadingDialog = null
    }
    
    private fun showAiResultDialog(title: String, content: String, canApply: Boolean) {
        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle(title)
            .setMessage(content)
            
        if (canApply) {
            dialog.setPositiveButton("Apply") { _, _ ->
                // Extract code from the response and apply it
                val codePattern = "```\\w*\\n(.*?)\\n```".toRegex(RegexOption.DOT_MATCHES_ALL)
                val match = codePattern.find(content)
                val extractedCode = match?.groupValues?.get(1) ?: content
                codeEditor.setText(extractedCode)
                saveProject()
            }
        }
        
        dialog.setNegativeButton("Close", null)
            .show()
    }
    
    private fun handleAiError(result: com.pocketdev.data.remote.GroqResult<*>) {
        val message = when (result) {
            is com.pocketdev.data.remote.GroqResult.Error -> result.message
            com.pocketdev.data.remote.GroqResult.NetworkError -> "No internet connection"
            com.pocketdev.data.remote.GroqResult.RateLimitError -> "Rate limit exceeded"
            com.pocketdev.data.remote.GroqResult.InvalidKeyError -> "Invalid API key"
            else -> "Unknown error"
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
    
    private fun showApiKeyRequiredDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("API Key Required")
            .setMessage("Please set your Groq API key in Settings to use AI features.")
            .setPositiveButton("Go to Settings") { _, _ ->
                startActivity(Intent(this, com.pocketdev.ui.settings.SettingsActivity::class.java))
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun clearConsole() {
        consoleOutput.text = ""
    }
    
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_editor, menu)
        return true
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                saveProject()
                finish()
                true
            }
            R.id.action_save -> {
                saveProject()
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_run -> {
                runCode()
                true
            }
            R.id.action_clear_console -> {
                clearConsole()
                true
            }
            R.id.action_ai -> {
                showAiFeatures()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    
    override fun onPause() {
        super.onPause()
        saveProject()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        autoSaveJob?.cancel()
        currentProject?.language?.let {
            executionManager.stopExecution(it)
        }
    }
}

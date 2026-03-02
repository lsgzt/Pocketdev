package com.pocketdev.ui.examples

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.pocketdev.R
import com.pocketdev.data.ExamplesLibrary
import com.pocketdev.data.local.database.AppDatabase
import com.pocketdev.data.local.entity.ProjectEntity
import com.pocketdev.model.ProgrammingLanguage
import com.pocketdev.ui.editor.EditorActivity
import kotlinx.coroutines.launch

class ExamplesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExamplesAdapter
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_examples)
        
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.examples_title)
        }
        
        setupTabLayout()
        setupRecyclerView()
        
        // Load Python examples by default
        loadExamples(ProgrammingLanguage.PYTHON)
    }
    
    private fun setupTabLayout() {
        tabLayout = findViewById(R.id.tab_layout)
        
        // Add tabs for each executable language
        tabLayout.addTab(tabLayout.newTab().setText("Python"))
        tabLayout.addTab(tabLayout.newTab().setText("JavaScript"))
        tabLayout.addTab(tabLayout.newTab().setText("HTML"))
        
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val language = when (tab?.position) {
                    0 -> ProgrammingLanguage.PYTHON
                    1 -> ProgrammingLanguage.JAVASCRIPT
                    2 -> ProgrammingLanguage.HTML
                    else -> ProgrammingLanguage.PYTHON
                }
                loadExamples(language)
            }
            
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }
    
    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recycler_examples)
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        adapter = ExamplesAdapter { example ->
            createProjectFromExample(example)
        }
        
        recyclerView.adapter = adapter
    }
    
    private fun loadExamples(language: ProgrammingLanguage) {
        val examples = ExamplesLibrary.getExamplesByLanguage(language)
        adapter.submitList(examples)
    }
    
    private fun createProjectFromExample(example: com.pocketdev.data.CodeExample) {
        lifecycleScope.launch {
            val project = ProjectEntity(
                name = example.title,
                language = example.language,
                code = example.code
            )
            
            val projectId = AppDatabase.getDatabase(this@ExamplesActivity)
                .projectDao()
                .insertProject(project)
            
            // Open the project in editor
            val intent = Intent(this@ExamplesActivity, EditorActivity::class.java)
            intent.putExtra(EditorActivity.EXTRA_PROJECT_ID, projectId)
            startActivity(intent)
            finish()
        }
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

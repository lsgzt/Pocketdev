package com.pocketdev.ui.projects

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pocketdev.R
import com.pocketdev.data.local.database.AppDatabase
import com.pocketdev.data.local.entity.ProjectEntity
import com.pocketdev.model.ProgrammingLanguage
import com.pocketdev.model.SortOption
import com.pocketdev.ui.MainActivity
import com.pocketdev.ui.editor.EditorActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProjectsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProjectsAdapter
    private lateinit var projectDao: com.pocketdev.data.local.dao.ProjectDao
    
    private var currentSortOption = SortOption.DATE_MODIFIED
    private var searchQuery = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_projects, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        projectDao = AppDatabase.getDatabase(requireContext()).projectDao()
        
        setupRecyclerView(view)
        setupFab(view)
        setupSearch(view)
        loadProjects()
    }
    
    private fun setupRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recycler_projects)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        
        adapter = ProjectsAdapter(
            onProjectClick = { project ->
                openProject(project.id)
            },
            onProjectLongClick = { project, anchorView ->
                showProjectMenu(project, anchorView)
            }
        )
        
        recyclerView.adapter = adapter
    }
    
    private fun setupFab(view: View) {
        val fab = view.findViewById<FloatingActionButton>(R.id.fab_new_project)
        fab?.setOnClickListener {
            showNewProjectDialog()
        }
    }
    
    private fun setupSearch(view: View) {
        val searchView = view.findViewById<SearchView>(R.id.search_view)
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            
            override fun onQueryTextChange(newText: String?): Boolean {
                searchQuery = newText ?: ""
                loadProjects()
                return true
            }
        })
    }
    
    private fun loadProjects() {
        lifecycleScope.launch {
            val flow = if (searchQuery.isNotEmpty()) {
                projectDao.searchProjects(searchQuery)
            } else {
                projectDao.getAllProjects()
            }
            
            flow.collectLatest { projects ->
                val sortedProjects = when (currentSortOption) {
                    SortOption.NAME -> projects.sortedBy { it.name.lowercase() }
                    SortOption.DATE_MODIFIED -> projects.sortedByDescending { it.modifiedAt }
                    SortOption.LANGUAGE -> projects.sortedBy { it.language.displayName }
                }
                adapter.submitList(sortedProjects)
            }
        }
    }
    
    private fun showNewProjectDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_new_project, null)
        val nameInput = dialogView.findViewById<EditText>(R.id.edit_project_name)
        
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.new_project_title)
            .setView(dialogView)
            .setPositiveButton(R.string.new_project_create) { _, _ ->
                val name = nameInput.text.toString().trim()
                if (name.isNotEmpty()) {
                    showLanguageSelectionDialog(name)
                }
            }
            .setNegativeButton(R.string.new_project_cancel, null)
            .show()
    }
    
    private fun showLanguageSelectionDialog(projectName: String) {
        val languages = ProgrammingLanguage.values()
        val languageNames = languages.map { it.displayName }.toTypedArray()
        
        var selectedLanguage = ProgrammingLanguage.PYTHON
        
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.new_project_language)
            .setSingleChoiceItems(languageNames, 0) { _, which ->
                selectedLanguage = languages[which]
            }
            .setPositiveButton(R.string.new_project_create) { _, _ ->
                createProject(projectName, selectedLanguage)
            }
            .setNegativeButton(R.string.new_project_cancel, null)
            .show()
    }
    
    private fun createProject(name: String, language: ProgrammingLanguage) {
        lifecycleScope.launch {
            val defaultCode = when (language) {
                ProgrammingLanguage.PYTHON -> "# Welcome to your new Python project!\n\nprint(\"Hello, World!\")"
                ProgrammingLanguage.JAVASCRIPT -> "// Welcome to your new JavaScript project!\n\nconsole.log(\"Hello, World!\");"
                ProgrammingLanguage.HTML -> HtmlExecutionEngine.getDefaultHtmlTemplate()
                ProgrammingLanguage.CSS -> "/* Welcome to your new CSS project! */\n\nbody {\n    font-family: Arial, sans-serif;\n}"
                ProgrammingLanguage.JAVA -> "// Welcome to your new Java project!\n\npublic class Main {\n    public static void main(String[] args) {\n        System.out.println(\"Hello, World!\");\n    }\n}"
                ProgrammingLanguage.CPP -> "// Welcome to your new C++ project!\n\n#include <iostream>\n\nint main() {\n    std::cout << \"Hello, World!\" << std::endl;\n    return 0;\n}"
                ProgrammingLanguage.KOTLIN -> "// Welcome to your new Kotlin project!\n\nfun main() {\n    println(\"Hello, World!\")\n}"
                ProgrammingLanguage.JSON -> "{\n  \"message\": \"Hello, World!\"\n}"
            }
            
            val project = ProjectEntity(
                name = name,
                language = language,
                code = defaultCode
            )
            
            val projectId = projectDao.insertProject(project)
            openProject(projectId)
        }
    }
    
    private fun openProject(projectId: Long) {
        val intent = Intent(requireContext(), EditorActivity::class.java)
        intent.putExtra(EditorActivity.EXTRA_PROJECT_ID, projectId)
        startActivity(intent)
    }
    
    private fun showProjectMenu(project: ProjectEntity, anchorView: View) {
        val popup = PopupMenu(requireContext(), anchorView)
        popup.menuInflater.inflate(R.menu.menu_project, popup.menu)
        
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_rename -> {
                    showRenameDialog(project)
                    true
                }
                R.id.action_duplicate -> {
                    duplicateProject(project)
                    true
                }
                R.id.action_delete -> {
                    showDeleteConfirmation(project)
                    true
                }
                else -> false
            }
        }
        
        popup.show()
    }
    
    private fun showRenameDialog(project: ProjectEntity) {
        val editText = EditText(requireContext()).apply {
            setText(project.name)
            selectAll()
        }
        
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Rename Project")
            .setView(editText)
            .setPositiveButton("Rename") { _, _ ->
                val newName = editText.text.toString().trim()
                if (newName.isNotEmpty()) {
                    lifecycleScope.launch {
                        projectDao.updateProject(project.copy(name = newName))
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun duplicateProject(project: ProjectEntity) {
        lifecycleScope.launch {
            val newProject = project.copy(
                id = 0,
                name = "${project.name} (Copy)",
                createdAt = java.util.Date(),
                modifiedAt = java.util.Date()
            )
            projectDao.insertProject(newProject)
            Toast.makeText(requireContext(), "Project duplicated", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun showDeleteConfirmation(project: ProjectEntity) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.project_delete_confirm, project.name))
            .setMessage(R.string.project_delete_message)
            .setPositiveButton("Delete") { _, _ ->
                lifecycleScope.launch {
                    projectDao.deleteProject(project)
                    Toast.makeText(requireContext(), "Project deleted", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    fun setSortOption(option: SortOption) {
        currentSortOption = option
        loadProjects()
    }
}

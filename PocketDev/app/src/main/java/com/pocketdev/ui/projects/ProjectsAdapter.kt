package com.pocketdev.ui.projects

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pocketdev.R
import com.pocketdev.data.local.entity.ProjectEntity
import java.text.SimpleDateFormat
import java.util.*

class ProjectsAdapter(
    private val onProjectClick: (ProjectEntity) -> Unit,
    private val onProjectLongClick: (ProjectEntity, View) -> Unit
) : ListAdapter<ProjectEntity, ProjectsAdapter.ProjectViewHolder>(ProjectDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_project, parent, false)
        return ProjectViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        
        private val nameText: TextView = itemView.findViewById(R.id.text_project_name)
        private val languageText: TextView = itemView.findViewById(R.id.text_language)
        private val dateText: TextView = itemView.findViewById(R.id.text_date)
        private val languageIcon: ImageView = itemView.findViewById(R.id.icon_language)
        private val moreButton: View = itemView.findViewById(R.id.button_more)
        
        private val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

        fun bind(project: ProjectEntity) {
            nameText.text = project.name
            languageText.text = project.language.displayName
            dateText.text = dateFormat.format(project.modifiedAt)
            
            // Set language icon color
            val context = itemView.context
            val colorRes = when (project.language) {
                com.pocketdev.model.ProgrammingLanguage.PYTHON -> R.color.badge_python
                com.pocketdev.model.ProgrammingLanguage.JAVASCRIPT -> R.color.badge_javascript
                com.pocketdev.model.ProgrammingLanguage.HTML -> R.color.badge_html
                com.pocketdev.model.ProgrammingLanguage.CSS -> R.color.badge_css
                com.pocketdev.model.ProgrammingLanguage.JAVA -> R.color.badge_java
                com.pocketdev.model.ProgrammingLanguage.CPP -> R.color.badge_cpp
                com.pocketdev.model.ProgrammingLanguage.KOTLIN -> R.color.badge_kotlin
                com.pocketdev.model.ProgrammingLanguage.JSON -> R.color.badge_json
            }
            languageIcon.setColorFilter(context.getColor(colorRes))
            
            // Click listeners
            itemView.setOnClickListener {
                onProjectClick(project)
            }
            
            moreButton.setOnClickListener { view ->
                onProjectLongClick(project, view)
            }
        }
    }

    class ProjectDiffCallback : DiffUtil.ItemCallback<ProjectEntity>() {
        override fun areItemsTheSame(oldItem: ProjectEntity, newItem: ProjectEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProjectEntity, newItem: ProjectEntity): Boolean {
            return oldItem == newItem
        }
    }
}

package com.pocketdev.ui.examples

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pocketdev.R
import com.pocketdev.data.CodeExample

class ExamplesAdapter(
    private val onLoadClick: (CodeExample) -> Unit
) : ListAdapter<CodeExample, ExamplesAdapter.ExampleViewHolder>(ExampleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_example, parent, false)
        return ExampleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        
        private val titleText: TextView = itemView.findViewById(R.id.text_title)
        private val descriptionText: TextView = itemView.findViewById(R.id.text_description)
        private val categoryText: TextView = itemView.findViewById(R.id.text_category)
        private val loadButton: Button = itemView.findViewById(R.id.button_load)

        fun bind(example: CodeExample) {
            titleText.text = example.title
            descriptionText.text = example.description
            categoryText.text = example.category.name.replace("_", " ")
            
            loadButton.setOnClickListener {
                onLoadClick(example)
            }
        }
    }

    class ExampleDiffCallback : DiffUtil.ItemCallback<CodeExample>() {
        override fun areItemsTheSame(oldItem: CodeExample, newItem: CodeExample): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CodeExample, newItem: CodeExample): Boolean {
            return oldItem == newItem
        }
    }
}

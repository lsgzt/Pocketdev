package com.pocketdev.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.pocketdev.R
import com.pocketdev.data.local.preferences.SettingsPreferences
import com.pocketdev.ui.MainActivity

class OnboardingActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var nextButton: Button
    private lateinit var skipButton: Button
    private lateinit var getStartedButton: Button
    
    private lateinit var settingsPreferences: SettingsPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        
        settingsPreferences = SettingsPreferences(this)
        
        initViews()
        setupViewPager()
        setupButtons()
    }
    
    private fun initViews() {
        viewPager = findViewById(R.id.view_pager)
        tabLayout = findViewById(R.id.tab_layout)
        nextButton = findViewById(R.id.button_next)
        skipButton = findViewById(R.id.button_skip)
        getStartedButton = findViewById(R.id.button_get_started)
    }
    
    private fun setupViewPager() {
        val onboardingItems = listOf(
            OnboardingItem(
                R.drawable.ic_code,
                getString(R.string.onboarding_editor_title),
                getString(R.string.onboarding_editor_desc)
            ),
            OnboardingItem(
                R.drawable.ic_run,
                getString(R.string.onboarding_execution_title),
                getString(R.string.onboarding_execution_desc)
            ),
            OnboardingItem(
                R.drawable.ic_book,
                getString(R.string.onboarding_ai_title),
                getString(R.string.onboarding_ai_desc)
            )
        )
        
        val adapter = OnboardingAdapter(onboardingItems)
        viewPager.adapter = adapter
        
        TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()
        
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateButtons(position)
            }
        })
    }
    
    private fun setupButtons() {
        nextButton.setOnClickListener {
            val nextItem = viewPager.currentItem + 1
            if (nextItem < (viewPager.adapter?.itemCount ?: 0)) {
                viewPager.currentItem = nextItem
            }
        }
        
        skipButton.setOnClickListener {
            finishOnboarding()
        }
        
        getStartedButton.setOnClickListener {
            finishOnboarding()
        }
    }
    
    private fun updateButtons(position: Int) {
        val isLastPage = position == (viewPager.adapter?.itemCount ?: 1) - 1
        
        if (isLastPage) {
            nextButton.visibility = View.GONE
            skipButton.visibility = View.GONE
            getStartedButton.visibility = View.VISIBLE
        } else {
            nextButton.visibility = View.VISIBLE
            skipButton.visibility = View.VISIBLE
            getStartedButton.visibility = View.GONE
        }
    }
    
    private fun finishOnboarding() {
        settingsPreferences.setOnboardingCompleted(true)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}

data class OnboardingItem(
    val imageRes: Int,
    val title: String,
    val description: String
)

class OnboardingAdapter(private val items: List<OnboardingItem>) : 
    androidx.recyclerview.widget.RecyclerView.Adapter<OnboardingAdapter.ViewHolder>() {
    
    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): ViewHolder {
        val view = android.view.LayoutInflater.from(parent.context)
            .inflate(R.layout.item_onboarding, parent, false)
        return ViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }
    
    override fun getItemCount(): Int = items.size
    
    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.image_onboarding)
        private val titleText: TextView = itemView.findViewById(R.id.text_title)
        private val descriptionText: TextView = itemView.findViewById(R.id.text_description)
        
        fun bind(item: OnboardingItem) {
            imageView.setImageResource(item.imageRes)
            titleText.text = item.title
            descriptionText.text = item.description
        }
    }
}

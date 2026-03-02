package com.pocketdev.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pocketdev.R
import com.pocketdev.data.local.preferences.SettingsPreferences
import com.pocketdev.model.ThemeMode
import com.pocketdev.ui.editor.EditorActivity
import com.pocketdev.ui.examples.ExamplesActivity
import com.pocketdev.ui.onboarding.OnboardingActivity
import com.pocketdev.ui.projects.ProjectsFragment
import com.pocketdev.ui.settings.SettingsActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var settingsPreferences: SettingsPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        
        settingsPreferences = SettingsPreferences(this)
        
        // Check if onboarding is completed
        if (!settingsPreferences.isOnboardingCompleted()) {
            startActivity(Intent(this, OnboardingActivity::class.java))
            finish()
            return
        }
        
        // Apply theme
        applyTheme()
        
        setContentView(R.layout.activity_main)
        
        setupBottomNavigation()
        
        // Load default fragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ProjectsFragment())
                .commit()
        }
    }
    
    private fun applyTheme() {
        when (settingsPreferences.getTheme()) {
            ThemeMode.DARK -> setTheme(R.style.Theme_PocketDev)
            ThemeMode.LIGHT -> setTheme(R.style.Theme_PocketDev_Light)
            ThemeMode.AUTO -> {
                // Follow system theme
            }
        }
    }
    
    private fun setupBottomNavigation() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_projects -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, ProjectsFragment())
                        .commit()
                    true
                }
                R.id.nav_examples -> {
                    startActivity(Intent(this, ExamplesActivity::class.java))
                    false
                }
                R.id.nav_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    false
                }
                else -> false
            }
        }
    }
    
    fun openEditor(projectId: Long? = null) {
        val intent = Intent(this, EditorActivity::class.java)
        projectId?.let { intent.putExtra(EditorActivity.EXTRA_PROJECT_ID, it) }
        startActivity(intent)
    }
}

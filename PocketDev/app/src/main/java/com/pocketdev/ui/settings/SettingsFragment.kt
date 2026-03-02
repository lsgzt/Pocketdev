package com.pocketdev.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.preference.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pocketdev.R
import com.pocketdev.data.local.preferences.SettingsPreferences
import com.pocketdev.model.AiModel
import com.pocketdev.model.FontSize
import com.pocketdev.model.TabSize
import com.pocketdev.model.ThemeMode

class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var settingsPreferences: SettingsPreferences

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        
        settingsPreferences = SettingsPreferences(requireContext())
        
        setupThemePreference()
        setupFontSizePreference()
        setupTabSizePreference()
        setupApiKeyPreference()
        setupAiModelPreference()
        setupAutoSavePreference()
        setupAutocompletePreference()
        setupLineNumbersPreference()
        setupResetPreference()
        setupAboutPreferences()
    }
    
    private fun setupThemePreference() {
        findPreference<ListPreference>("theme")?.apply {
            setOnPreferenceChangeListener { _, newValue ->
                val theme = ThemeMode.valueOf(newValue as String)
                settingsPreferences.setTheme(theme)
                
                // Restart activity to apply theme
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Restart Required")
                    .setMessage("Please restart the app to apply the theme change.")
                    .setPositiveButton("Restart") { _, _ ->
                        val intent = requireActivity().packageManager
                            .getLaunchIntentForPackage(requireActivity().packageName)
                        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                        requireActivity().finish()
                    }
                    .setNegativeButton("Later", null)
                    .show()
                
                true
            }
        }
    }
    
    private fun setupFontSizePreference() {
        findPreference<ListPreference>("font_size")?.apply {
            setOnPreferenceChangeListener { _, newValue ->
                val fontSize = FontSize.valueOf(newValue as String)
                settingsPreferences.setFontSize(fontSize)
                true
            }
        }
    }
    
    private fun setupTabSizePreference() {
        findPreference<ListPreference>("tab_size")?.apply {
            setOnPreferenceChangeListener { _, newValue ->
                val tabSize = TabSize.valueOf(newValue as String)
                settingsPreferences.setTabSize(tabSize)
                true
            }
        }
    }
    
    private fun setupApiKeyPreference() {
        findPreference<EditTextPreference>("api_key")?.apply {
            setOnPreferenceChangeListener { _, newValue ->
                val apiKey = (newValue as String).trim()
                
                if (apiKey.isNotEmpty() && !apiKey.startsWith("gsk_")) {
                    Toast.makeText(
                        requireContext(),
                        "Invalid API key format. Should start with 'gsk_'",
                        Toast.LENGTH_LONG
                    ).show()
                    false
                } else {
                    settingsPreferences.setApiKey(apiKey.ifEmpty { null })
                    Toast.makeText(requireContext(), "API key saved", Toast.LENGTH_SHORT).show()
                    true
                }
            }
            
            // Show masked value
            setSummaryProvider {
                if (settingsPreferences.hasApiKey()) {
                    "API key is set"
                } else {
                    "Not set"
                }
            }
        }
        
        findPreference<Preference>("api_key_help")?.setOnPreferenceClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://console.groq.com/keys"))
            startActivity(intent)
            true
        }
    }
    
    private fun setupAiModelPreference() {
        findPreference<ListPreference>("ai_model")?.apply {
            setOnPreferenceChangeListener { _, newValue ->
                val model = AiModel.valueOf(newValue as String)
                settingsPreferences.setAiModel(model)
                true
            }
        }
    }
    
    private fun setupAutoSavePreference() {
        findPreference<SwitchPreferenceCompat>("auto_save")?.apply {
            setOnPreferenceChangeListener { _, newValue ->
                settingsPreferences.setAutoSave(newValue as Boolean)
                true
            }
        }
    }
    
    private fun setupAutocompletePreference() {
        findPreference<SwitchPreferenceCompat>("autocomplete")?.apply {
            setOnPreferenceChangeListener { _, newValue ->
                settingsPreferences.setAutocomplete(newValue as Boolean)
                true
            }
        }
    }
    
    private fun setupLineNumbersPreference() {
        findPreference<SwitchPreferenceCompat>("line_numbers")?.apply {
            setOnPreferenceChangeListener { _, newValue ->
                settingsPreferences.setLineNumbers(newValue as Boolean)
                true
            }
        }
    }
    
    private fun setupResetPreference() {
        findPreference<Preference>("reset_settings")?.setOnPreferenceClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.settings_reset_confirm)
                .setMessage("This will reset all settings to their default values. Your projects will not be affected.")
                .setPositiveButton("Reset") { _, _ ->
                    settingsPreferences.resetToDefaults()
                    Toast.makeText(requireContext(), "Settings reset", Toast.LENGTH_SHORT).show()
                    
                    // Restart to apply changes
                    val intent = requireActivity().packageManager
                        .getLaunchIntentForPackage(requireActivity().packageName)
                    intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    requireActivity().finish()
                }
                .setNegativeButton("Cancel", null)
                .show()
            true
        }
    }
    
    private fun setupAboutPreferences() {
        findPreference<Preference>("version")?.summary = "1.0.0"
        
        findPreference<Preference>("privacy_policy")?.setOnPreferenceClickListener {
            // Open privacy policy
            true
        }
    }
}

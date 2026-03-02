package com.pocketdev.data.local.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.pocketdev.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsPreferences(context: Context) {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val securePrefs: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        "secure_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private val regularPrefs: SharedPreferences = context.getSharedPreferences(
        "pocketdev_settings",
        Context.MODE_PRIVATE
    )

    // API Key (Encrypted)
    fun getApiKey(): String? {
        return securePrefs.getString(KEY_API_KEY, null)
    }

    fun setApiKey(apiKey: String?) {
        securePrefs.edit {
            putString(KEY_API_KEY, apiKey)
        }
    }

    fun hasApiKey(): Boolean {
        return !getApiKey().isNullOrBlank()
    }

    // Theme
    fun getTheme(): ThemeMode {
        val value = regularPrefs.getString(KEY_THEME, ThemeMode.DARK.name)
        return try {
            ThemeMode.valueOf(value ?: ThemeMode.DARK.name)
        } catch (e: IllegalArgumentException) {
            ThemeMode.DARK
        }
    }

    fun setTheme(theme: ThemeMode) {
        regularPrefs.edit {
            putString(KEY_THEME, theme.name)
        }
        _themeFlow.value = theme
    }

    private val _themeFlow = MutableStateFlow(getTheme())
    val themeFlow: StateFlow<ThemeMode> = _themeFlow.asStateFlow()

    // Font Size
    fun getFontSize(): FontSize {
        val value = regularPrefs.getString(KEY_FONT_SIZE, FontSize.MEDIUM.name)
        return try {
            FontSize.valueOf(value ?: FontSize.MEDIUM.name)
        } catch (e: IllegalArgumentException) {
            FontSize.MEDIUM
        }
    }

    fun setFontSize(fontSize: FontSize) {
        regularPrefs.edit {
            putString(KEY_FONT_SIZE, fontSize.name)
        }
    }

    fun getFontSizeSp(): Float {
        return when (getFontSize()) {
            FontSize.SMALL -> 12f
            FontSize.MEDIUM -> 14f
            FontSize.LARGE -> 16f
        }
    }

    // Tab Size
    fun getTabSize(): TabSize {
        val value = regularPrefs.getString(KEY_TAB_SIZE, TabSize.FOUR.name)
        return try {
            TabSize.valueOf(value ?: TabSize.FOUR.name)
        } catch (e: IllegalArgumentException) {
            TabSize.FOUR
        }
    }

    fun setTabSize(tabSize: TabSize) {
        regularPrefs.edit {
            putString(KEY_TAB_SIZE, tabSize.name)
        }
    }

    // Auto Save
    fun isAutoSaveEnabled(): Boolean {
        return regularPrefs.getBoolean(KEY_AUTO_SAVE, true)
    }

    fun setAutoSave(enabled: Boolean) {
        regularPrefs.edit {
            putBoolean(KEY_AUTO_SAVE, enabled)
        }
    }

    // Autocomplete
    fun isAutocompleteEnabled(): Boolean {
        return regularPrefs.getBoolean(KEY_AUTOCOMPLETE, true)
    }

    fun setAutocomplete(enabled: Boolean) {
        regularPrefs.edit {
            putBoolean(KEY_AUTOCOMPLETE, enabled)
        }
    }

    // Line Numbers
    fun isLineNumbersEnabled(): Boolean {
        return regularPrefs.getBoolean(KEY_LINE_NUMBERS, true)
    }

    fun setLineNumbers(enabled: Boolean) {
        regularPrefs.edit {
            putBoolean(KEY_LINE_NUMBERS, enabled)
        }
    }

    // AI Model
    fun getAiModel(): AiModel {
        val value = regularPrefs.getString(KEY_AI_MODEL, AiModel.LLAMA.name)
        return try {
            AiModel.valueOf(value ?: AiModel.LLAMA.name)
        } catch (e: IllegalArgumentException) {
            AiModel.LLAMA
        }
    }

    fun setAiModel(model: AiModel) {
        regularPrefs.edit {
            putString(KEY_AI_MODEL, model.name)
        }
    }

    // Onboarding
    fun isOnboardingCompleted(): Boolean {
        return regularPrefs.getBoolean(KEY_ONBOARDING_COMPLETED, false)
    }

    fun setOnboardingCompleted(completed: Boolean) {
        regularPrefs.edit {
            putBoolean(KEY_ONBOARDING_COMPLETED, completed)
        }
    }

    // Reset to defaults
    fun resetToDefaults() {
        regularPrefs.edit {
            clear()
        }
        securePrefs.edit {
            remove(KEY_API_KEY)
        }
        _themeFlow.value = ThemeMode.DARK
    }

    companion object {
        private const val KEY_API_KEY = "api_key"
        private const val KEY_THEME = "theme"
        private const val KEY_FONT_SIZE = "font_size"
        private const val KEY_TAB_SIZE = "tab_size"
        private const val KEY_AUTO_SAVE = "auto_save"
        private const val KEY_AUTOCOMPLETE = "autocomplete"
        private const val KEY_LINE_NUMBERS = "line_numbers"
        private const val KEY_AI_MODEL = "ai_model"
        private const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"
    }
}

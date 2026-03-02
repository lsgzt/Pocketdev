package com.pocketdev

import android.app.Application
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.pocketdev.data.local.database.AppDatabase
import com.pocketdev.data.local.preferences.SettingsPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class PocketDevApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    
    val database by lazy { AppDatabase.getDatabase(this) }
    val projectRepository by lazy { database.projectDao() }
    val settingsPreferences by lazy { SettingsPreferences(this) }

    override fun onCreate() {
        super.onCreate()
        instance = this
        
        // Initialize Chaquopy Python
        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(this))
        }
    }

    companion object {
        lateinit var instance: PocketDevApplication
            private set
    }
}

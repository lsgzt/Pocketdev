package com.pocketdev.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pocketdev.data.local.converter.DateConverter
import com.pocketdev.data.local.converter.LanguageConverter
import com.pocketdev.data.local.dao.ProjectDao
import com.pocketdev.data.local.entity.ProjectEntity

@Database(
    entities = [ProjectEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class, LanguageConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun projectDao(): ProjectDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "pocketdev_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

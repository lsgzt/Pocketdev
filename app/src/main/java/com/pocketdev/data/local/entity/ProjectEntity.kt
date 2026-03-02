package com.pocketdev.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.pocketdev.data.local.converter.DateConverter
import com.pocketdev.data.local.converter.LanguageConverter
import com.pocketdev.model.ProgrammingLanguage
import java.util.Date

@Entity(tableName = "projects")
@TypeConverters(DateConverter::class, LanguageConverter::class)
data class ProjectEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val language: ProgrammingLanguage,
    val code: String,
    val createdAt: Date = Date(),
    val modifiedAt: Date = Date()
)

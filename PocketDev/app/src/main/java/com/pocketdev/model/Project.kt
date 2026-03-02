package com.pocketdev.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "projects")
data class Project(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val language: ProgrammingLanguage,
    val code: String,
    val createdAt: Long = System.currentTimeMillis(),
    val modifiedAt: Long = System.currentTimeMillis()
) {
    fun getFileExtension(): String = language.fileExtension
    
    fun getDisplayName(): String = if (name.endsWith(language.fileExtension)) {
        name
    } else {
        "${name}.${language.fileExtension}"
    }
}

enum class ProgrammingLanguage(
    val displayName: String,
    val fileExtension: String,
    val mimeType: String,
    val isExecutable: Boolean,
    val colorRes: String
) {
    PYTHON(
        displayName = "Python",
        fileExtension = "py",
        mimeType = "text/x-python",
        isExecutable = true,
        colorRes = "badge_python"
    ),
    JAVASCRIPT(
        displayName = "JavaScript",
        fileExtension = "js",
        mimeType = "application/javascript",
        isExecutable = true,
        colorRes = "badge_javascript"
    ),
    HTML(
        displayName = "HTML",
        fileExtension = "html",
        mimeType = "text/html",
        isExecutable = true,
        colorRes = "badge_html"
    ),
    CSS(
        displayName = "CSS",
        fileExtension = "css",
        mimeType = "text/css",
        isExecutable = false,
        colorRes = "badge_css"
    ),
    JAVA(
        displayName = "Java",
        fileExtension = "java",
        mimeType = "text/x-java",
        isExecutable = false,
        colorRes = "badge_java"
    ),
    CPP(
        displayName = "C++",
        fileExtension = "cpp",
        mimeType = "text/x-c++src",
        isExecutable = false,
        colorRes = "badge_cpp"
    ),
    KOTLIN(
        displayName = "Kotlin",
        fileExtension = "kt",
        mimeType = "text/x-kotlin",
        isExecutable = false,
        colorRes = "badge_kotlin"
    ),
    JSON(
        displayName = "JSON",
        fileExtension = "json",
        mimeType = "application/json",
        isExecutable = false,
        colorRes = "badge_json"
    );

    companion object {
        fun fromExtension(extension: String): ProgrammingLanguage? {
            return values().find { it.fileExtension.equals(extension, ignoreCase = true) }
        }
        
        fun fromDisplayName(name: String): ProgrammingLanguage? {
            return values().find { it.displayName.equals(name, ignoreCase = true) }
        }
    }
}

enum class SortOption {
    NAME,
    DATE_MODIFIED,
    LANGUAGE
}

enum class ThemeMode {
    DARK,
    LIGHT,
    AUTO
}

enum class FontSize {
    SMALL,
    MEDIUM,
    LARGE
}

enum class TabSize(val spaces: Int) {
    TWO(2),
    FOUR(4)
}

enum class AiModel(val modelId: String, val displayName: String) {
    LLAMA("llama-3.3-70b-versatile", "Llama 3.3 70B"),
    MIXTRAL("mixtral-8x7b-32768", "Mixtral 8x7B")
}

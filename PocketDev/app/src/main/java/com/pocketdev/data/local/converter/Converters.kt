package com.pocketdev.data.local.converter

import androidx.room.TypeConverter
import com.pocketdev.model.ProgrammingLanguage
import java.util.Date

class DateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}

class LanguageConverter {
    @TypeConverter
    fun fromLanguage(language: ProgrammingLanguage?): String? {
        return language?.name
    }

    @TypeConverter
    fun toLanguage(value: String?): ProgrammingLanguage? {
        return value?.let { ProgrammingLanguage.valueOf(it) }
    }
}

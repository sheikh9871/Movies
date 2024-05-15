package com.example.buildProduct.database

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson

class TypeConverter {
    @TypeConverter
    fun listToJson(value: MutableList<String?>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String?): MutableList<String?>? {
        val objects = Gson().fromJson(value, Array<String?>::class.java)
        return objects?.toMutableList()
    }
}
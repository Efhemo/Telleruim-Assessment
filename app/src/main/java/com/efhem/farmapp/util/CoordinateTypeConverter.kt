package com.efhem.farmapp.util

import androidx.room.TypeConverter
import com.efhem.farmapp.domain.model.Coordinate
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable
import java.lang.reflect.Type

class CoordinateTypeConverter : Serializable {
    @TypeConverter
    fun fromCoordinates(users: List<Coordinate?>?): String? {
        if (users == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Coordinate?>?>() {}.type
        return gson.toJson(users, type)
    }

    @TypeConverter
    fun toCoordinates(usersString: String?): List<Coordinate>? {
        if (usersString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Coordinate?>?>() {}.type
        return gson.fromJson<List<Coordinate>>(usersString, type)
    }
}
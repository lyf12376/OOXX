package com.yi.xxoo.Room.converter

import androidx.room.TypeConverter

class UserConverters {
    @TypeConverter
    fun fromString(value: String?): ArrayList<Int> {
        if (value.isNullOrEmpty()) return arrayListOf()
        return value.split(",").map { it.toInt() }.toCollection(ArrayList())
    }

    @TypeConverter
    fun fromArrayList(arrayList: ArrayList<Int>?): String {
        if (arrayList.isNullOrEmpty()) return ""
        return arrayList.joinToString(separator = ",")
    }
}

package com.yi.xxoo.utils

object RoomUtils {

    fun String.stringToPersonalBestRecord(): ArrayList<Int> {
        if (this.isEmpty()) return arrayListOf() // 修改检查空字符串的方法
        // 修改对每个分割后的元素调用toInt，并正确处理可能的转换异常
        return try {
            this.split(",").map { it.trim().toInt() } // 使用 it 代替 this，并去除可能的空白符
                .toCollection(ArrayList())
        } catch (e: NumberFormatException) {
            arrayListOf() // 如果有不能转换的字符串，返回空的ArrayList
        }
    }
    fun ArrayList<Int>.personalBestRecordToString():String
    {
        if (this.isEmpty()) return ""
        return this.joinToString(separator = ",")
    }
}
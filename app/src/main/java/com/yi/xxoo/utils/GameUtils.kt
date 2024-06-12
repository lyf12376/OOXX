package com.yi.xxoo.utils

object GameUtils {
    // 将一个字符串转换为一个 N x N 的二维列表
    fun expandStringToNxNList(input: String): List<List<Char>> {
        val length = input.length
        val n = kotlin.math.sqrt(length.toDouble()).toInt()
        require(n * n == length) { "The length of the string must be a perfect square." }

        return (0 until n).map { i ->
            input.substring(i * n, (i + 1) * n).toList()
        }
    }


}

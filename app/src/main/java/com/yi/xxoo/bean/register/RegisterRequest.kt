package com.yi.xxoo.bean.register

data class RegisterRequest(
    val email: String,
    val password: String,
    val verificationCode: String
)

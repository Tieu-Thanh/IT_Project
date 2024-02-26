package com.example.loginui.data.authen

data class SignUpRequest(
    val email: String,
    val password: String,
    val returnSecureToken: Boolean = true,
    )
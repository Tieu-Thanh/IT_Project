package com.example.loginui.data

data class SignInRequest(
    val email: String,
    val password: String,
    val returnSecureToken: Boolean = true,
    val tenantId: String? = null,
)


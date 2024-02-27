package com.example.loginui.data.authen

data class SignUpResponse(
    val idToken: String,
    val email: String,
    val refreshToken: String,
    val expiresIn: String? = null,
    val localId: String
)
package com.example.loginui.data

data class SignInResponse(
    val idToken: String,
    val email: String,
    val refreshToken: String,
    val expiresIn: String,
    val localId: String,
    val registered: Boolean,

)

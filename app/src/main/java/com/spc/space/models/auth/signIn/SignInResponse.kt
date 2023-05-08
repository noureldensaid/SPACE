package com.spc.space.models.auth.signIn

data class SignInResponse(
    val message: String,
    val status: String,
    val token: String
)
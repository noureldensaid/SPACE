package com.spc.space.models.auth.signIn

data class SignInRequest(
    val email: String,
    val password: String,
)

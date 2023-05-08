package com.spc.space.models.auth.signUp

data class SignUpRequest(
    val userName: String,
    val email: String,
    val password: String,
    val cPassword: String
)

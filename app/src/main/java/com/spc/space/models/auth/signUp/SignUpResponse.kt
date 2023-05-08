package com.spc.space.models.auth.signUp

data class SignUpResponse(
    val message: String,
    val savedUser: SavedUser
)
package com.spc.space.models.auth.signUp

data class SignUpResponse(
    val message: String,
    val savedUser: SavedUser
)
data class SavedUser(
    val confirmEmail: Boolean,
    val email: String,
    val favorites: List<Any>,
    val password: String,
    val role: String,
    val updatedAt: String,
    val userName: String
)
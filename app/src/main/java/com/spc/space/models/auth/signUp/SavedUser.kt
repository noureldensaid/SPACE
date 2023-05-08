package com.spc.space.models.auth.signUp

data class SavedUser(
    val confirmEmail: Boolean,
    val email: String,
    val favorites: List<Any>,
    val password: String,
    val role: String,
    val updatedAt: String,
    val userName: String
)
package com.spc.space.models.updateProfile

data class UpdateProfileRequest(
    val profilePic: String = "noPic",
    val username: String,
    val email: String,
    val phone: String
)

package com.spc.space.models.updateProfile

import com.google.gson.annotations.SerializedName

data class UpdateProfileResponse(
    val message: String,
    val updatedUser: UpdatedUser
)

data class UpdatedUser(
    @SerializedName("_id")
    val id: String,
    val confirmEmail: Boolean,
    val createdAt: String,
    val email: String,
    val password: String,
    val phone: Int,
    val profilePic: String,
    val userName: String
)
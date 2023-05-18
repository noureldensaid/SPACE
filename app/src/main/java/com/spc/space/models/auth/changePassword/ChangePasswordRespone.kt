package com.spc.space.models.auth.changePassword

import com.google.gson.annotations.SerializedName

data class ChangePasswordRespone(
    val message: String,
    val updated: Updated
)

data class Updated(
    val OTPCode: Any,
    @SerializedName("_id")
    val id: String,
    val adminValidation: Boolean,
    val confirmEmail: Boolean,
    val createdAt: String,
    val email: String,
    val favorites: List<Any>,
    val password: String,
    val role: String,
    val updatedAt: String,
    val userName: String
)
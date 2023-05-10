package com.spc.space.models.favs

import com.google.gson.annotations.SerializedName

// Note : this class will be used only to check if item was added successfully or not

data class AddFavoritesResponse(
    val message: String,
    @SerializedName("updated")
    val item: Items
)

data class Items(
    @SerializedName("_id")
    val id: String,
    val adminValidation: Boolean,
    val confirmEmail: Boolean,
    val createdAt: String,
    val email: String,
    val favorites: List<String>,
    val password: String,
    val role: String,
    val updatedAt: String,
    val userName: String
)
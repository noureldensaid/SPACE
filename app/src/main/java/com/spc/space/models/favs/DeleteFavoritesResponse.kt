package com.spc.space.models.favs

import com.google.gson.annotations.SerializedName

data class DeleteFavoritesResponse(
    val message: String,
    @SerializedName("updated")
    val deletedItem: DeletedItem
)

data class DeletedItem(
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
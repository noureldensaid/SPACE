package com.spc.space.models.favs

data class GetFavoritesResponse(
    val message: String,
    val favorites: List<String>
)
package com.spc.space.models.favs

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetFavoritesResponse(
    @SerializedName("founded")
    val favoriteItems: FavoriteItem,
    val message: String
) : Parcelable

@Parcelize
data class FavoriteItem(
    val favorites: List<Favorite>,
) : Parcelable

@Parcelize
data class Favorite(
    @SerializedName("_id")
    val id: String,
    val avgRate: Int,
    val contact: Contact,
    val description: String,
    val images: List<String>,
    val location: Location,
    val name: String,
    val schedule: Schedule,
) : Parcelable

@Parcelize
data class Contact(
    val email: List<String>,
    val phone: List<Int>,
    val socialMedia: List<String>
) : Parcelable

@Parcelize
data class Location(
    val buildingNumber: String,
    val city: String,
    val latitude: String,
    val longitude: String,
    val region: String,
    val streetName: String
) : Parcelable

@Parcelize
data class Schedule(
    val closingTime: String,
    val holidays: List<String>,
    val openingTime: String
) : Parcelable
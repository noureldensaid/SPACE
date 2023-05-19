package com.spc.space.models.userdata

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDataResponse(
    val message: String,
    val user: SavedUser
) : Parcelable

@Parcelize
data class SavedUser(
    @SerializedName("_id")
    val _id: String,
    val email: String,
    val password: String,
    val phone: Int,
    val profilePic: String,
    val userName: String
) : Parcelable
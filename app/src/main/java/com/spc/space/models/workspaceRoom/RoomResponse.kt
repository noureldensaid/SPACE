package com.spc.space.models.workspaceRoom

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RoomResponse(
    val message: String,
    val room: List<RoomItem>? = emptyList(),
) : Parcelable

@Parcelize
data class RoomItem(
    @SerializedName("_id")
    val id: String? = null,
    val capacity: Int? = null,
    val roomImages: List<String>? = emptyList(),
    val roomName: String? = null,
    val roomNumber: String? = null,
    val type: String? = null,
    val workspaceId: String? = null,
) : Parcelable
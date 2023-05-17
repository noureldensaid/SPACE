package com.spc.space.models.bookingsHistory

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class BookingHistoryResponse(
    val message: String,
    val history: List<History>
)

@Parcelize
data class History(
//    val createdAt: String,
//    val dateCreated: String,
    @SerializedName("_id") val id: String,
    val duration: Float,
    val endTime: String,
    val isCancelled: Boolean? = null,
    val isDone: Boolean? = null,
    val isUpcoming: Boolean? = null,
    val isMissed: Boolean? = null,
    val price: Float,
    val room: Room,
    val startTime: String,
):Parcelable
@Parcelize

data class Room(
    @SerializedName("_id")
    val id: String,
    val price: Int,
    val capacity: Int,
    val roomImages: List<String>,
    val roomName: String,
    val roomNumber: String,
    val type: String,
    @SerializedName("workspaceId")
    val workspace: Workspace
):Parcelable
@Parcelize

data class Workspace(
    @SerializedName("_id") val id: String,
    val avgRate: Float,
    val images: List<String>,
    val location: Location,
    val name: String,
    val schedule: Schedule,
):Parcelable
@Parcelize

data class Location(
    val buildingNumber: String,
    val city: String,
    val latitude: String,
    val longitude: String,
    val region: String,
    val streetName: String
):Parcelable
@Parcelize

data class Schedule(
    @SerializedName("_id") val id: String,
    val closingTime: String,
    val holidays: List<String>,
    val openingTime: String
):Parcelable



package com.spc.space.models.canceledBookingsHistory

import com.google.gson.annotations.SerializedName

data class CanceledBookingsResponse(
    val history: List<CanceledHistory>, val message: String
)

data class CanceledHistory(
//    val createdAt: String,
//    val dateCreated: String,
    @SerializedName("_id") val id: String,
    val duration: Float,
    val endTime: String,
    val dateCreated: String,
    val isCancelled: Boolean,
    val isMissed: Boolean? = null,
    val price: Float,
    val room: Room,
    val startTime: String,
)

data class Room(
    @SerializedName("_id") val id: String,
    val price: Int,
    val roomImages: List<String>,
    val roomName: String,
    val roomNumber: String,
    val type: String,
    @SerializedName("workspaceId")
    val workspace: Workspace
)

data class Workspace(
    @SerializedName("_id") val id: String,
    val avgRate: Float,
    val images: List<String>,
    val location: Location,
    val name: String,
    val schedule: Schedule,
)

data class Contact(
    val email: List<String>,
    val phone: List<Int>,
    val socialMedia: List<String>
)

data class Location(
    val buildingNumber: String,
    val city: String,
    val latitude: String,
    val longitude: String,
    val region: String,
    val streetName: String
)

data class Schedule(
    @SerializedName("_id") val id: String,
    val closingTime: String,
    val holidays: List<String>,
    val openingTime: String
)

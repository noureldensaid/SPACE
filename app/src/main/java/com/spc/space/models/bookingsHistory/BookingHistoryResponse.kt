package com.spc.space.models.bookingsHistory

import com.google.gson.annotations.SerializedName

data class BookingHistoryResponse(
    val message: String,
    val history: List<History>
)

data class History(
//    val createdAt: String,
//    val dateCreated: String,
    @SerializedName("_id") val id: String,
    val duration: Float,
    val endTime: String,
    val isCancelled: Boolean,
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
    val workspaceId: WorkspaceId
)

data class WorkspaceId(
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



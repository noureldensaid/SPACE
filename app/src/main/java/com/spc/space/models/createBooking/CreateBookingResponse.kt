package com.spc.space.models.createBooking

import com.google.gson.annotations.SerializedName

data class CreateBookingResponse(
    val message: String,
    val addedBooking: AddedBooking?
)

data class AddedBooking(
    @SerializedName("_id")
    val id: String,
    val createdAt: String,
    val dateCreated: String,
    val duration: Double,
    val endTime: String,
    val isCancelled: Boolean,
    val price: Double,
    val room: String,
    val startTime: String,
    val updatedAt: String,
    val user: String
)
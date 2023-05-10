package com.spc.space.models.createBooking

import com.google.gson.annotations.SerializedName

data class CreateBookingRequest(
    @SerializedName("room")
    val roomId: String,
    val endTime: String,
    val startTime: String
)
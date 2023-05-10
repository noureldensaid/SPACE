package com.spc.space.models.createBooking

import com.google.gson.annotations.SerializedName

data class CreateBookingRequest(
    @SerializedName("room")
    val roomId: String,
    val endTime: String,
    val startTime: String
)

//{
//    "room":"6457a2897a4e51c466691169",
//    "startTime":"2023-05-07T15:00:00+02:00",
//    "endTime":"2023-05-07T16:30:00+02:00"
//}
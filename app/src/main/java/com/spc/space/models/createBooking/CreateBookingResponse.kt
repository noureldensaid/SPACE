package com.spc.space.models.createBooking

data class CreateBookingResponse(
    val addedBooking: AddedBooking,
    val message: String
)
data class AddedBooking(
    val __v: Int,
    val _id: String,
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
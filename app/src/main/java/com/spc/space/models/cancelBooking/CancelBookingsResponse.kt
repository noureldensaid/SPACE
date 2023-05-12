package com.spc.space.models.cancelBooking

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CancelBookingsResponse(
    @SerializedName("bookingCancellation")
    val bookingCancellationInfo: BookingCancellationInfo,
    val message: String
) : Parcelable

@Parcelize
data class BookingCancellationInfo(
    @SerializedName("_id")
    val bookingId: String,
    val createdAt: String,
    val dateCreated: String,
    val duration: Int,
    val endTime: String,
    val isCancelled: Boolean,
    val price: Int,
    val room: String,
    val startTime: String,
    val updatedAt: String,
    val user: String
) : Parcelable
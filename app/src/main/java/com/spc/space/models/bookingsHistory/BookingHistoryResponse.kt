package com.spc.space.models.bookingsHistory

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookingHistoryResponse(
    val message: String,
    val history: List<History>
) : Parcelable

@Parcelize
data class History(
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
) : Parcelable



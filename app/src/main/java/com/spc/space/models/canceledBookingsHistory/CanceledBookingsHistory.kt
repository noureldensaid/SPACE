package com.spc.space.models.canceledBookingsHistory

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CanceledBookingsHistory(
    val history: List<History>,
    val message: String
) : Parcelable

@Parcelize
data class History(
    @SerializedName("_id")
    val id: String,
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
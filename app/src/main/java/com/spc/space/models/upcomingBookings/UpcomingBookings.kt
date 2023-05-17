package com.spc.space.models.upcomingBookings

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UpcomingBookings(
    val bookingId: String,
    val wsName: String,
    val roomName: String,
    val roomImg: String,
    val region: String,
    val capacity: Int,
    val startTime: String,
    val endTime: String,
    val duration: String,
    val price: String,
) : Parcelable


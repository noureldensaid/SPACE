package com.spc.space.models.upcoming

data class UpcomingDto(
    val id: String? = null,
    val createdAt: String? = null,
    val dateCreated: String? = null,
    val duration: Double,
    val endTime: String? = null,
    val isCancelled: Boolean,
    val price: String? = null,
    val room: String? = null,
    val startTime: String? = null,
    val updatedAt: String? = null,
    val user: String? = null,
)
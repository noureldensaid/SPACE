package com.spc.space.models.workspace

import com.google.gson.annotations.SerializedName

data class Schedule(
    @SerializedName("_id")
    val id: String,
    val closingTime: String,
    val holidays: List<String>,
    val openingTime: String
)
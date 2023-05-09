package com.spc.space.models.workspace

import com.google.gson.annotations.SerializedName

data class Contact(
    @SerializedName("_id")
    val id: String,
    val email: List<String>,
    val phone: List<Int>,
    val socialMedia: List<String>
)
package com.spc.space.models.workspace

import com.google.gson.annotations.SerializedName

data class WorkSpace(
    val __v: Int,
    @SerializedName("_id")
    val id: String,
    val avgRate: Int,
    val contact: Contact,
    val createdAt: String,
    val dateCreated: String,
    val description: String,
    val images: List<String>,
    val latitude: String,
    val longitude: String,
    val name: String,
    val ownerId: String,
    val publicImageIds: List<String>,
    val schedule: Schedule,
    val updatedAt: String
)
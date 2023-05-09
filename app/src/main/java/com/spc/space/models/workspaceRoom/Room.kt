package com.spc.space.models.workspaceRoom

data class Room(
    val __v: Int,
    val _id: String,
    val airConditioning: Boolean,
    val capacity: Int,
    val chairs: Boolean,
    val computer: Boolean,
    val dateCreated: String,
    val desk: Boolean,
    val heating: Boolean,
    val microphone: Boolean,
    val monitor: Boolean,
    val price: Int,
    val printer: Boolean,
    val projectors: Boolean,
    val publicImageIds: List<String>,
    val roomImages: List<String>,
    val roomName: String,
    val roomNumber: String,
    val scanner: Boolean,
    val smartTv: Boolean,
    val type: String,
    val wifi: Boolean,
    val workspaceId: String
)
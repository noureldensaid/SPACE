package com.spc.space.models.WorkSpaceRooms

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class RoomsResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("room")
	val room: List<RoomItem>? = emptyList(),
) : Parcelable

@Parcelize
data class RoomItem(

	@field:SerializedName("airConditioning")
	val airConditioning: Boolean? = null,

	@field:SerializedName("wifi")
	val wifi: Boolean? = null,

	@field:SerializedName("roomNumber")
	val roomNumber: String? = null,

	@field:SerializedName("projectors")
	val projectors: Boolean? = null,

	@field:SerializedName("printer")
	val printer: Boolean? = null,

	@field:SerializedName("monitor")
	val monitor: Boolean? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("roomName")
	val roomName: String? = null,

	@field:SerializedName("capacity")
	val capacity: Int? = null,

	@field:SerializedName("smartTv")
	val smartTv: Boolean? = null,

	@field:SerializedName("computer")
	val computer: Boolean? = null,

	@field:SerializedName("dateCreated")
	val dateCreated: String? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("desk")
	val desk: Boolean? = null,

	@field:SerializedName("__v")
	val v: Int? = null,

	@field:SerializedName("scanner")
	val scanner: Boolean? = null,

	@field:SerializedName("chairs")
	val chairs: Boolean? = null,

	@field:SerializedName("roomImages")
	val roomImages: List<String?>? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("microphone")
	val microphone: Boolean? = null,

	@field:SerializedName("heating")
	val heating: Boolean? = null,

	@field:SerializedName("publicImageIds")
	val publicImageIds: List<String?>? = null,

	@field:SerializedName("workspaceId")
	val workspaceId: String? = null
) : Parcelable

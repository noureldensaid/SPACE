package com.spc.space.models.workspaces

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class WorkspacesResponse(

	@field:SerializedName("workSpace")
	val workSpace: List<WorkSpaceItem>? = emptyList(),

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class WorkSpaceSchedule(

	@field:SerializedName("holidays")
	val holidays: List<String?>? = null,

	@field:SerializedName("closingTime")
	val closingTime: String? = null,

	@field:SerializedName("openingTime")
	val openingTime: String? = null,

	@field:SerializedName("_id")
	val id: String? = null
) : Parcelable

@Parcelize
data class WorkspaceContact(

	@field:SerializedName("phone")
	val phone: List<Int?>? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("socialMedia")
	val socialMedia: List<String?>? = null,

	@field:SerializedName("email")
	val email: List<String?>? = null
) : Parcelable

@Parcelize
data class WorkSpaceItem(

	@field:SerializedName("images")
	val images: List<String?>? = null,

	@field:SerializedName("latitude")
	val latitude: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("ownerId")
	val ownerId: String? = null,

	@field:SerializedName("schedule")
	val workSpaceSchedule: WorkSpaceSchedule? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("dateCreated")
	val dateCreated: String? = null,

	@field:SerializedName("contact")
	val workspaceContact: WorkspaceContact? = null,

	@field:SerializedName("__v")
	val v: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("avgRate")
	val avgRate: Int? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("publicImageIds")
	val publicImageIds: List<String?>? = null,

	@field:SerializedName("longitude")
	val longitude: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
) : Parcelable

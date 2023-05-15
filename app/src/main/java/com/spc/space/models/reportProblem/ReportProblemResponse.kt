package com.spc.space.models.reportProblem

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ReportProblemResponse(

	@field:SerializedName("savedReport")
	val savedReport: SavedReport? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class Workspace(

	@field:SerializedName("images")
	val images: List<String?>? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("ownerId")
	val ownerId: String? = null,

	@field:SerializedName("schedule")
	val schedule: Schedule? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("dateCreated")
	val dateCreated: String? = null,

	@field:SerializedName("contact")
	val contact: Contact? = null,

	@field:SerializedName("__v")
	val v: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("avgRate")
	val avgRate: Int? = null,

	@field:SerializedName("location")
	val location: Location? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("publicImageIds")
	val publicImageIds: List<String?>? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
) : Parcelable

@Parcelize
data class CreatedBy(

	@field:SerializedName("favorites")
	val favorites: List<String?>? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("adminValidation")
	val adminValidation: Boolean? = null,

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("__v")
	val v: Int? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("userName")
	val userName: String? = null,

	@field:SerializedName("confirmEmail")
	val confirmEmail: Boolean? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
) : Parcelable

@Parcelize
data class Schedule(

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
data class Location(

	@field:SerializedName("streetName")
	val streetName: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("latitude")
	val latitude: String? = null,

	@field:SerializedName("buildingNumber")
	val buildingNumber: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("region")
	val region: String? = null,

	@field:SerializedName("longitude")
	val longitude: String? = null
) : Parcelable

@Parcelize
data class SavedReport(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("workspace")
	val workspace: Workspace? = null,

	@field:SerializedName("createdBy")
	val createdBy: CreatedBy? = null,

	@field:SerializedName("__v")
	val v: Int? = null,

	@field:SerializedName("report")
	val report: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
) : Parcelable

@Parcelize
data class Contact(

	@field:SerializedName("phone")
	val phone: List<Int?>? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("socialMedia")
	val socialMedia: List<String?>? = null,

	@field:SerializedName("email")
	val email: List<String?>? = null
) : Parcelable

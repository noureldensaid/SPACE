package com.spc.space.models.updatePassword

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class UpdatePasswordResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("updatedUser")
	val updatedUser: UpdatedUser? = null
) : Parcelable

@Parcelize
data class UpdatedUser(

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

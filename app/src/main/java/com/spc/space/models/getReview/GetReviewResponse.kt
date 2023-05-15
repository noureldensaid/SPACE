package com.spc.space.models.getReview

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class GetReviewResponse(

	@field:SerializedName("avgRating")
	val avgRating: Float? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

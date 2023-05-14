package com.spc.space.models.createReviewRequest

import com.google.gson.annotations.SerializedName

data class CreateReviewResponse(
    val message: String,
    val review: Review
)

data class Review(
    @SerializedName("_id")
    val id: String,
    val createdAt: String,
    val createdBy: String,
    val date: String,
    val rating: Int,
    val updatedAt: String,
    val workspace: String
)
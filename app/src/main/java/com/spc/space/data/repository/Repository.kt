package com.spc.space.data.repository

import com.spc.space.data.remote.SpaceApi
import com.spc.space.models.createBooking.CreateBookingRequest
import com.spc.space.models.createReviewRequest.CreateReviewRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val spaceApi: SpaceApi,
) {
    suspend fun getWorkspaces(token: String) =
        spaceApi.getWorkspaces("Bearer__$token")

    suspend fun getRoomsForWorkspace(workspaceId: String) =
        spaceApi.getRoomsForWorkspace(workspaceId)

    suspend fun createBooking(token: String, bookingRequest: CreateBookingRequest) =
        spaceApi.createBooking("Bearer__$token", bookingRequest)


    suspend fun cancelBooking(token: String, bookingId: String) =
        spaceApi.cancelBooking("Bearer__$token", bookingId)

    suspend fun getBookingsHistory(token: String) =
        spaceApi.getBookingsHistory("Bearer__$token")

    suspend fun getCanceledBookingsHistory(token: String) =
        spaceApi.getCanceledBookingsHistory("Bearer__$token")


    suspend fun addToFavorites(token: String, workspaceId: String) =
        spaceApi.addToFavorites("Bearer__$token", workspaceId)


    suspend fun removeFromFavorites(token: String, workspaceId: String) =
        spaceApi.removeFromFavorites("Bearer__$token", workspaceId)


    suspend fun getFavorites(token: String) = spaceApi.getFavorites("Bearer__$token")

    suspend fun createReview(
        token: String,
        workspaceId: String,
        createReviewRequest: CreateReviewRequest
    ) =
        spaceApi.createReview("Bearer__$token", workspaceId, createReviewRequest)


}
package com.spc.space.data.repository

import com.spc.space.data.remote.SpaceApi
import com.spc.space.models.createBooking.CreateBookingRequest
import com.spc.space.models.createReviewRequest.CreateReviewRequest
import com.spc.space.models.reportProblem.ReportProblemRequest
import com.spc.space.models.updatePassword.UpdatePasswordRequest
import com.spc.space.models.updateProfile.UpdateProfileRequest
import okhttp3.RequestBody
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

    suspend fun getReview(token: String, workspaceId: String) =
        spaceApi.getReview("Bearer__$token", workspaceId)


    suspend fun createReportProblem(
        token: String,
        workspaceId: String,
        problemRequest: ReportProblemRequest
    ) =
        spaceApi.createReportProblem("Bearer__$token", workspaceId, problemRequest)


    suspend fun updatePassword(
        token: String,
        updatePasswordRequest: UpdatePasswordRequest
    ) =
        spaceApi.updatePassword("Bearer__$token", updatePasswordRequest)

    suspend fun updateProfilePic(token: String, image: RequestBody) =
        spaceApi.updateProfilePic("Bearer__$token", image)


    suspend fun getUserData(token: String) =
        spaceApi.getUserData("Bearer__$token")

    suspend fun updateUserProfile(token: String, userId: String, req: UpdateProfileRequest) =
        spaceApi.updateUserProfile("Bearer__$token", userId, req)


}


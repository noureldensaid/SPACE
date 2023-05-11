package com.spc.space.data.repository

import com.spc.space.data.remote.SpaceApi
import com.spc.space.models.createBooking.CreateBookingRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val spaceApi: SpaceApi,
) {
    suspend fun getWorkspaces(token: String) = spaceApi.getWorkspaces(token)
    suspend fun getRoomsForWorkspace(workspaceId: String) =
        spaceApi.getRoomsForWorkspace(workspaceId)

    suspend fun createBooking(token: String, bookingRequest: CreateBookingRequest) =
        spaceApi.createBooking(token,bookingRequest)

}
package com.spc.space.data.repository

import com.spc.space.data.remote.SpaceApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val spaceApi: SpaceApi,
) {
    suspend fun getData(token: String) = spaceApi.getWorkSpaces(token)
}
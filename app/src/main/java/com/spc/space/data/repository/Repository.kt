package com.spc.space.data.repository

import com.spc.space.data.local.UnsplashDatabase
import com.spc.space.data.remote.SpaceApi
import com.spc.space.data.remote.UnsplashApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val spaceAPI: SpaceApi,
    private val unsplashDatabase: UnsplashDatabase
) {
    suspend fun getData(token:String) = spaceAPI.getWorkSpaces(token)
}
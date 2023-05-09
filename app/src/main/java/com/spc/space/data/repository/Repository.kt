package com.spc.space.data.repository

import com.spc.space.data.local.UnsplashDatabase
import com.spc.space.data.remote.SpaceApi
import com.spc.space.data.remote.UnsplashApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val unsplashApi: UnsplashApi,
    private val spaceApi: SpaceApi,
    private val unsplashDatabase: UnsplashDatabase
) {
    suspend fun getData() = unsplashApi.searchPhotos()


    suspend fun getWorkspaces(token: String) = spaceApi.getWorkspaces(token)


}
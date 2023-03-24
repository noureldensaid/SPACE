package com.spc.space.data.repository

import com.spc.space.data.local.UnsplashDatabase
import com.spc.space.data.remote.UnsplashApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val unsplashApi: UnsplashApi,
    private val unsplashDatabase: UnsplashDatabase
) {
    suspend fun getData() = unsplashApi.searchPhotos()
}
package com.spc.space.data.remote

import com.spc.space.models.UnsplashPhoto
import com.spc.space.models.UnsplashResponse
import com.spc.space.utils.Constants.ACCESS_KEY
 import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashApi {

    @Headers("Accept-Version: v1", "Authorization: Client-ID $ACCESS_KEY")
    @GET("/photos")
    suspend fun getAllImages(): Response<UnsplashResponse>

    @Headers("Accept-Version: v1", "Authorization: Client-ID $ACCESS_KEY")
    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String = "Workspace"
    ): Response<UnsplashResponse>
}
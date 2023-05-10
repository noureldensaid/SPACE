package com.spc.space.data.remote

import com.spc.space.models.auth.signIn.SignInRequest
import com.spc.space.models.auth.signIn.SignInResponse
import com.spc.space.models.auth.signUp.SignUpRequest
import com.spc.space.models.auth.signUp.SignUpResponse
import com.spc.space.models.createBooking.CreateBookingRequest
import com.spc.space.models.createBooking.CreateBookingResponse
import com.spc.space.models.favs.AddFavoritesResponse
import com.spc.space.models.favs.GetFavoritesResponse
import com.spc.space.models.workspace.WorkspacesResponse
import com.spc.space.models.workspaceRoom.RoomResponse
import retrofit2.Call
import retrofit2.http.*

interface SpaceApi {

    // sign up
    @POST("auth/signUp")
    suspend fun signUp(
        @Body request: SignUpRequest
    ): SignUpResponse


    // sign in
    @POST("auth/signIn")
    suspend fun signIn(
        @Body request: SignInRequest
    ): SignInResponse

    // get all workspaces
    // don't forget to add Bearer__ before passing the token --> Bearer__$token
    @GET("workingSpace/getWorkSpaces")
    suspend fun getWorkspaces(
        @Header("authorization") userToken: String,
    ): WorkspacesResponse


    // get specific room for a certain workspace
    @GET("room/getRoomsForSpecificWs/{workspaceId}")
    suspend fun getRoomsForWorkspace(
        @Path("workspaceId") workspaceId: String
    ): RoomResponse


    //TODO : CREATE A BOOKING
    @POST("booking/createBooking")
    fun createBooking(
        // don't forget to add Bearer__ before passing the token --> Bearer__$token
        @Header("authorization") userToken: String,
        @Body request: CreateBookingRequest
    ): CreateBookingResponse


    // Favs api feh mshakel lesa sebko menha


    //TODO : ADD WORKSPACE TO FAVS
    @PUT("favorite/addFavorites/{workspaceId}")
    fun addToFavorites(
        @Header("authorization") userToken: String,
        @Path("workspaceId") workspaceId: String
    ): AddFavoritesResponse // Replace `Any` with the response data class if available


    //TODO : GET FAVOURITES
    @GET("favorite/getFavorites")
    fun getFavorites(
        @Header("authorization") userToken: String
    ): GetFavoritesResponse

    //TODO : REMOVE WORK SPACE FROM FAVS BY SWIPING AND SHOW A SNAK BAR TO UNDO
    @PUT("favorite/removeFavorites/{workspaceId}")
    fun removeFromFavorites(
        @Header("authorization") userToken: String,
        @Path("workspaceId") workspaceId: String
    ): Call<Any> // Replace `Any` with the response data class if available


}
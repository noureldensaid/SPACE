package com.spc.space.data.remote

import com.spc.space.models.auth.signIn.SignInRequest
import com.spc.space.models.auth.signIn.SignInResponse
import com.spc.space.models.auth.signUp.SignUpRequest
import com.spc.space.models.auth.signUp.SignUpResponse
import com.spc.space.models.workspace.WorkSpaceResponse
import com.spc.space.models.workspaceRoom.workSpaceRoomResponse
import retrofit2.http.*

interface SpaceApi {

    // sign up
    @POST("auth/signUp")
    suspend fun signUp(
        @Body request: SignUpRequest
    ): SignUpResponse


    // sign in
    @POST("auth/signIn")
    suspend fun signIn(@Body request: SignInRequest): SignInResponse

    // get all workspaces
    // don't forget to add Bearer__ before passing the token --> Bearer__$token
    @GET("workingSpace/getWorkSpaces")
    suspend fun getWorkspaces(
        @Header("authorization") userToken: String,
    ): WorkSpaceResponse


    // get specific room for a certain workspace
    @GET("room/getRoomsForSpecificWs/{workspaceId}")
    suspend fun getRoomsForWorkspace(
        @Path("workspaceId") workspaceId: String
    ): workSpaceRoomResponse


}
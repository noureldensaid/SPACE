package com.spc.space.data.remote

import com.spc.space.models.auth.signIn.SignInRequest
import com.spc.space.models.auth.signIn.SignInResponse
import com.spc.space.models.auth.signUp.SignUpRequest
import com.spc.space.models.auth.signUp.SignUpResponse
import com.spc.space.models.workspaces.WorkspacesResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

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
    suspend fun getWorkSpaces(
        @Header("authorization") token: String
    ): Response<WorkspacesResponse>


}
package com.spc.space.data.remote

import com.spc.space.models.auth.changePassword.ChangePasswordRequest
import com.spc.space.models.auth.changePassword.ChangePasswordRespone
import com.spc.space.models.auth.forgetPassword.ForgetPasswordRequest
import com.spc.space.models.auth.forgetPassword.ForgetPasswordResponse
import com.spc.space.models.auth.signIn.SignInRequest
import com.spc.space.models.auth.signIn.SignInResponse
import com.spc.space.models.auth.signUp.SignUpRequest
import com.spc.space.models.auth.signUp.SignUpResponse
import com.spc.space.models.bookingsHistory.BookingHistoryResponse
import com.spc.space.models.cancelBooking.CancelBookingsResponse
import com.spc.space.models.canceledBookingsHistory.CanceledBookingsResponse
import com.spc.space.models.createBooking.CreateBookingRequest
import com.spc.space.models.createBooking.CreateBookingResponse
import com.spc.space.models.createReviewRequest.CreateReviewRequest
import com.spc.space.models.createReviewRequest.CreateReviewResponse
import com.spc.space.models.favs.AddFavoritesResponse
import com.spc.space.models.favs.DeleteFavoritesResponse
import com.spc.space.models.favs.GetFavoritesResponse
import com.spc.space.models.getReview.GetReviewResponse
import com.spc.space.models.reportProblem.ReportProblemRequest
import com.spc.space.models.reportProblem.ReportProblemResponse
import com.spc.space.models.updatePassword.UpdatePasswordRequest
import com.spc.space.models.updatePassword.UpdatePasswordResponse
import com.spc.space.models.updateProfile.UpdateProfileRequest
import com.spc.space.models.updateProfile.UpdateProfileResponse
import com.spc.space.models.updateProfilePic.UpdateProfilePicRequest
import com.spc.space.models.updateProfilePic.UpdateProfilePicResponse
import com.spc.space.models.userdata.UserDataResponse
import com.spc.space.models.workspace.WorkspacesResponse
import com.spc.space.models.workspaceRoom.RoomResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface SpaceApi {

    //change pass
    @POST("auth/forgetPassword")
    suspend fun changePassword(
        @Body request: ChangePasswordRequest
    ): ChangePasswordRespone


    // Forget password
    @POST("auth/sendCode")
    suspend fun forgetPassword(
        @Header("authorization") userToken: String,
        @Body request: ForgetPasswordRequest
    ): ForgetPasswordResponse


    @PATCH("user/updatePassword")
    suspend fun updatePassword(
        @Header("authorization") userToken: String,
        @Body requestBody: UpdatePasswordRequest,

        ): UpdatePasswordResponse


    // sign up
    @POST("auth/signUp")
    suspend fun signUp(
        @Body request: SignUpRequest
    ): SignUpResponse


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


    @POST("booking/createBooking")
    suspend fun createBooking(
        // don't forget to add Bearer__ before passing the token --> Bearer__$token
        @Header("authorization") userToken: String,
        @Body request: CreateBookingRequest
    ): CreateBookingResponse


    @PUT("booking/CancelBooking/{bookingId}")
    suspend fun cancelBooking(
        @Header("authorization") userToken: String,
        @Path("bookingId") bookingId: String
    ): CancelBookingsResponse


    @GET("booking/getBookingsHistoryToUser")
    suspend fun getBookingsHistory(
        @Header("authorization") userToken: String
    ): BookingHistoryResponse


    @GET("booking/cancelledBookingsHistoryToUser")
    suspend fun getCanceledBookingsHistory(
        @Header("authorization") userToken: String
    ): CanceledBookingsResponse


    @PUT("favorite/addFavorites/{workspaceId}")
    suspend fun addToFavorites(
        @Header("authorization") userToken: String,
        @Path("workspaceId") workspaceId: String
    ): AddFavoritesResponse // Replace `Any` with the response data class if available


    @GET("favorite/getFavorites")
    suspend fun getFavorites(
        @Header("authorization") userToken: String
    ): GetFavoritesResponse

    @PUT("favorite/removeFavorites/{workspaceId}")
    suspend fun removeFromFavorites(
        @Header("authorization") userToken: String,
        @Path("workspaceId") workspaceId: String
    ): DeleteFavoritesResponse


    @POST("workingSpace/createReview/{workspaceId}")
    suspend fun createReview(
        @Header("authorization") userToken: String,
        @Path("workspaceId") workspaceId: String,
        @Body requestBody: CreateReviewRequest,
    ): CreateReviewResponse


    @GET("workingSpace/avgRate/{workspaceId}")
    suspend fun getReview(
        @Header("authorization") userToken: String,
        @Path("workspaceId") workspaceId: String
    ): GetReviewResponse


    @POST("user/UserReportWs/{workspaceId}")
    suspend fun createReportProblem(
        @Header("authorization") userToken: String,
        @Path("workspaceId") workspaceId: String,
        @Body requestBody: ReportProblemRequest,
    ): ReportProblemResponse

    @GET("user/getUserProfile")
    suspend fun getUserData(
        @Header("authorization") userToken: String,
    ): UserDataResponse


    @PUT("user/profilePic")
    suspend fun updateProfilePic(
        @Header("authorization") userToken: String,
        @Body image:  RequestBody
    ): UpdateProfilePicResponse


    @PUT("user/updateProfile/{userId}")
    suspend fun updateUserProfile(
        @Path("userId") userId: String,
        @Header("authorization") token: String,
        @Body request: UpdateProfileRequest
    ): UpdateProfileResponse




//    @Multipart
//    @PUT("updateProfile/{userId}")
//    fun updateProfile(
//        @Path("userId") userId: String,
//        @Header("Authorization") authToken: String,
//        @Part image: MultipartBody.Part?,
//        @QueryMap data: Map<String, String>
//    ): UpdateProfilePicResponse


}




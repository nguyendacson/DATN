package com.example.movieseeme.data.remote.api

import com.example.movieseeme.data.remote.model.ApiResponse
import com.example.movieseeme.data.remote.model.request.AvatarRequest
import com.example.movieseeme.data.remote.model.request.ChangePasswordRequest
import com.example.movieseeme.data.remote.model.request.UserUpdateRequest
import com.example.movieseeme.data.remote.model.request.auth.UploadResponse
import com.example.movieseeme.domain.model.user.InformationUser
import com.example.movieseeme.domain.model.user.Signature
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface UserAPI {
    @GET("users/myInfo")
    suspend fun getMyInfo(): Response<ApiResponse<InformationUser>>

    @GET("api/v1/cloudinary/signature")
    suspend fun getSignature(): Response<Signature>

    @Multipart
    @POST("https://api.cloudinary.com/v1_1/{cloudName}/image/upload")
    suspend fun uploadAvatar(
        @Path("cloudName") cloudName: String,
        @Part file: MultipartBody.Part,
        @Part("api_key") apiKey: RequestBody,
        @Part("timestamp") timestamp: RequestBody,
        @Part("folder") folder: RequestBody,
        @Part("signature") signature: RequestBody
    ): Response<UploadResponse>

    @PUT("users/avatar")
    suspend fun saveAvatar(
        @Body body: AvatarRequest
    ): Response<ApiResponse<String>>

    @PUT("users/update")
    suspend fun updateUser(
        @Body userUpdateRequest: UserUpdateRequest
    ): Response<ApiResponse<String>>

    @POST("users/change-password")
    suspend fun changeUserPassword(
        @Body changePasswordRequest: ChangePasswordRequest
    ): Response<ApiResponse<String>>

}
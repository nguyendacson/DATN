package com.example.movieseeme.domain.repository

import com.example.movieseeme.data.remote.model.ApiResponse
import com.example.movieseeme.data.remote.model.ApiResult
import com.example.movieseeme.data.remote.model.request.ChangePasswordRequest
import com.example.movieseeme.data.remote.model.request.UserUpdateRequest
import com.example.movieseeme.data.remote.model.request.auth.UploadResponse
import com.example.movieseeme.domain.model.user.InformationUser
import com.example.movieseeme.domain.model.user.Signature
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part

interface UserRepository {
    suspend fun getMyInfo(): ApiResult<ApiResponse<InformationUser>>
    suspend fun getSignature(): ApiResult<Signature>

    suspend fun uploadAvatar(
        cloudName: String,
        file: MultipartBody.Part,
        apiKey: RequestBody,
        timestamp: RequestBody,
        folder: RequestBody,
        signature: RequestBody
    ): ApiResult<UploadResponse>

    suspend fun saveAvatar(
        secure_url: String,
        public_id: String
    ): ApiResult<ApiResponse<String>>

    suspend fun updateUser(
        body: UserUpdateRequest
    ): ApiResult<ApiResponse<String>>

    suspend fun changeUserPassword(
        body: ChangePasswordRequest
    ): ApiResult<ApiResponse<String>>

}
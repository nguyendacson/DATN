package com.example.movieseeme.data.repository

import com.example.movieseeme.data.remote.api.UserAPI
import com.example.movieseeme.data.remote.model.ApiResponse
import com.example.movieseeme.data.remote.model.ApiResult
import com.example.movieseeme.data.remote.model.request.AvatarRequest
import com.example.movieseeme.data.remote.model.request.ChangePasswordRequest
import com.example.movieseeme.data.remote.model.request.UserUpdateRequest
import com.example.movieseeme.data.remote.model.request.auth.UploadResponse
import com.example.movieseeme.domain.model.user.InformationUser
import com.example.movieseeme.domain.model.user.Signature
import com.example.movieseeme.domain.repository.UserRepository
import com.google.gson.Gson
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named

class UserRepositoryImpl @Inject constructor(
    @param:Named("user_retrofit") private val userAPI: UserAPI
) : UserRepository {

    private suspend fun <T> safeApiCall(apiCall: suspend () -> Response<ApiResponse<T>>): ApiResult<ApiResponse<T>> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    ApiResult.Success(body)
                } else {
                    ApiResult.Error(response.code(), "Empty response body")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ApiResponse::class.java)
                } catch (e: Exception) {
                    null
                }
                ApiResult.Error(
                    code = response.code(),
                    message = errorResponse?.message ?: "Unknown error"
                )
            }
        } catch (e: IOException) {
            ApiResult.Error(null, "Network error: ${e.message}")
        } catch (e: Exception) {
            ApiResult.Error(null, "Unknown error: ${e.message}")
        }
    }

    private suspend fun <T> safeApiCallRaw(apiCall: suspend () -> Response<T>): ApiResult<T> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    ApiResult.Success(body)
                } else {
                    ApiResult.Error(response.code(), "Empty response body")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ApiResponse::class.java)
                } catch (e: Exception) {
                    null
                }

                ApiResult.Error(
                    code = errorResponse?.code ?: response.code(),
                    message = errorResponse?.message ?: errorBody ?: "Unknown error"
                )
            }
        } catch (e: IOException) {
            ApiResult.Error(null, "Network error: ${e.message}")
        } catch (e: Exception) {
            ApiResult.Error(null, "Unknown error: ${e.message}")
        }
    }


    override suspend fun getMyInfo(): ApiResult<ApiResponse<InformationUser>> {
        return safeApiCall { userAPI.getMyInfo() }
    }

    override suspend fun getSignature(): ApiResult<Signature> {
        return safeApiCallRaw { userAPI.getSignature() }
    }

    override suspend fun uploadAvatar(
        cloudName: String,
        file: MultipartBody.Part,
        apiKey: RequestBody,
        timestamp: RequestBody,
        folder: RequestBody,
        signature: RequestBody
    ): ApiResult<UploadResponse> {
        return safeApiCallRaw {
            userAPI.uploadAvatar(
                cloudName = cloudName,
                file = file,
                apiKey = apiKey,
                timestamp = timestamp,
                folder = folder,
                signature = signature
            )
        }
    }

    override suspend fun saveAvatar(
        secure_url: String,
        public_id: String
    ): ApiResult<ApiResponse<String>> {
        return safeApiCallRaw {
            userAPI.saveAvatar(
                AvatarRequest(
                    secure_url = secure_url,
                    public_id = public_id
                )
            )
        }
    }

    override suspend fun updateUser(body: UserUpdateRequest): ApiResult<ApiResponse<String>> {
        return safeApiCallRaw {
            userAPI.updateUser(userUpdateRequest = body)
        }
    }

    override suspend fun changeUserPassword(body: ChangePasswordRequest): ApiResult<ApiResponse<String>> {
        return safeApiCallRaw {
            userAPI.changeUserPassword(body)
        }
    }
}
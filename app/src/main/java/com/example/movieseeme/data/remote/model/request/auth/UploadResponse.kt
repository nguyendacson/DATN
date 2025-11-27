package com.example.movieseeme.data.remote.model.request.auth

import com.google.gson.annotations.SerializedName

data class UploadResponse(
    @SerializedName("asset_id")
    val assetId: String,

    @SerializedName("public_id")
    val publicId: String,

    val version: Long,

    @SerializedName("version_id")
    val versionId: String,

    val signature: String,
    val width: Int,
    val height: Int,
    val format: String,

    @SerializedName("resource_type")
    val resourceType: String,

    @SerializedName("created_at")
    val createdAt: String,

    val tags: List<String>,
    val bytes: Long,
    val type: String,
    val etag: String,
    val placeholder: Boolean,
    val url: String,

    @SerializedName("secure_url")
    val secureUrl: String,

    @SerializedName("asset_folder")
    val assetFolder: String,

    @SerializedName("display_name")
    val displayName: String,

    @SerializedName("original_filename")
    val originalFilename: String,

    @SerializedName("api_key")
    val apiKey: String
)
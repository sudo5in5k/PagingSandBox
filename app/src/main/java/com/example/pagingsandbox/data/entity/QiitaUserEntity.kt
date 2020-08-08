package com.example.pagingsandbox.data.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class QiitaUserEntity(
    val description: String?,
    @Json(name = "profile_image_url")
    val profileImageUrl: String,
    val id: String,
    @Json(name = "items_count")
    val itemsCount: Int?
) : Serializable
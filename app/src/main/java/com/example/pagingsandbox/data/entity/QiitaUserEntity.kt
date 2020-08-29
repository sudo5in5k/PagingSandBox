package com.example.pagingsandbox.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QiitaUserEntity(
    val description: String?,
    @SerialName("profile_image_url")
    val profileImageUrl: String,
    val id: String,
    @SerialName("items_count")
    val itemsCount: Int?,

)
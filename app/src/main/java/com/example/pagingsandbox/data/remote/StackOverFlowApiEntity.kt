package com.example.pagingsandbox.data.remote

data class StackOverFlowApiEntity(
    val items: List<Item>,
    val has_more: Boolean,
    val quota_max: Int,
    val quota_remaining: Int
)


data class Item(
    val owner: Owner,
    val is_accepted: Boolean,
    val score: Int,
    val last_activity_date: Long,
    val creation_date: Long,
    val answer_id: Long,
    val question_id: Long
)

data class Owner(
    val reputation: Int,
    val user_id: Long,
    val user_type: String,
    val profile_image: String,
    val display_name: String,
    val link: String
)
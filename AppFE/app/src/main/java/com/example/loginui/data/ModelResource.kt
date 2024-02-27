package com.example.loginui.data

import com.google.gson.annotations.SerializedName


data class ModelResource(
    @SerializedName("model_id") val modelId: String,
    @SerializedName("user_id") val userId: String,
    @SerializedName("model_name") val modelName: String,
    val classes: List<String>,
    @SerializedName("crawl_number") val crawlNumber: Int
)
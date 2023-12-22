package com.example.scanscience.data.response

import com.google.gson.annotations.SerializedName

data class SaveResponse (
    @field:SerializedName("success")
    val success: Boolean,
    @field:SerializedName("message")
    val message: String,
    @field:SerializedName("url")
    val url: String
)
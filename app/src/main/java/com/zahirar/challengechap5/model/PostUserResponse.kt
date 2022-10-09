package com.zahirar.challengechap5.model


import com.google.gson.annotations.SerializedName

data class PostUserResponse(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("username")
    val username: String
)
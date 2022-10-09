package com.zahirar.challengechap5.model


import com.google.gson.annotations.SerializedName

data class PostDataFilmItem(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("director")
    val director: String
)
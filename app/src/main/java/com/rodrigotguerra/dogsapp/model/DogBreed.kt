package com.rodrigotguerra.dogsapp.model

import com.google.gson.annotations.SerializedName

data class DogBreed(
    val id: String?,
    val name: String?,
    @SerializedName("life_span")
    val lifeSpan: String?,
    @SerializedName("breed_group")
    val breedGroup: String?,
    @SerializedName("bred_for")
    val bredFor: String?,
    val temperament: String?,
    @SerializedName("url")
    val imageUrl: String?
)
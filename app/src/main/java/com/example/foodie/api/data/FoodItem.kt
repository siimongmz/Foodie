package com.example.foodie.api.data

import com.google.gson.annotations.SerializedName

data class FoodItem(
    val code: String,
    val product: Product?,
    val status: Int,
    @SerializedName("status_verbose")
    val statusVerbose: String
)
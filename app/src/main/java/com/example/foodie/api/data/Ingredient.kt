package com.example.foodie.api.data

import com.google.gson.annotations.SerializedName

data class Ingredient(
    @SerializedName("ciqual_food_code")
    val ciqualFoodCode: String,
    @SerializedName("ciqual_proxy_food_code")
    val ciqualProxyFoodCode: String,
    @SerializedName("from_palm_oil")
    val fromPalmOil: String,
    val id: String,
    @SerializedName("percent_estimate")
    val percentEstimate: Double,
    @SerializedName("percent_max")
    val percentMax: Double,
    @SerializedName("percent_min")
    val percentMin: Double,
    val text: String,
    val vegan: String,
    val vegetarian: String
)
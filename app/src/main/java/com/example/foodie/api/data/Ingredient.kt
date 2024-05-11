package com.example.foodie.api.data

data class Ingredient(
    val ciqual_food_code: String,
    val ciqual_proxy_food_code: String,
    val from_palm_oil: String,
    val id: String,
    val percent_estimate: Double,
    val percent_max: Double,
    val percent_min: Double,
    val text: String,
    val vegan: String,
    val vegetarian: String
)
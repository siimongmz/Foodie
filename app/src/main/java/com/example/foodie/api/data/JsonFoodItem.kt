package com.example.foodie.api.data

data class JsonFoodItem(
    val code: String,
    val product: Product?,
    val status: Int,
    val status_verbose: String
)
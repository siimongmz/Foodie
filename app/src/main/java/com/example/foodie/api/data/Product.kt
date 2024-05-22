package com.example.foodie.api.data

import com.google.gson.annotations.SerializedName

data class Product(
    val allergens: String,
    val brands: String,
    val ingredients: List<Ingredient>,
    @SerializedName("product_name")
    val productName: String,
    @SerializedName("image_front_small_url")
    val imageFrontSmallUrl: String,
    @SerializedName("image_ingredients_url")
    val imageIngredientsUrl: String
)
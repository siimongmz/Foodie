package com.example.foodie.api

import com.example.foodie.api.data.FoodItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface FoodApiInterface {

    @GET("product/{code}?lc=es&?fields=product_name,ingredients,brands,allergens,image_front_small_url,image_ingredients_url.json")
    fun getData(@Path("code") codeId: String): Call<FoodItem>
}
package com.example.foodie.api


import com.example.foodie.api.data.JsonFoodItem
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FoodApi {
    val BASE_URL = "https://world.openfoodfacts.net/api/v2/"
    //Ejemplo    = "https://world.openfoodfacts.net/api/v2/product/3017624010701?fields=product_name,ingredients,brands,allergens"

    fun getProduct(code: String): JsonFoodItem? {
        var food: JsonFoodItem? = null
        val retrofitBuilder =
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(FoodApiInterface::class.java)
        val retrofitData = retrofitBuilder.getData(code)

        return retrofitData.execute().body()


    }
}
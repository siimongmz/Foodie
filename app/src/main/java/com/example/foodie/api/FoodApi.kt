package com.example.foodie.api


import android.util.Log
import com.example.foodie.api.data.JsonFoodItem
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FoodApi {
    companion object{
        private const val BASE_URL = "https://world.openfoodfacts.net/api/v2/"
    }

    //Ejemplo    = "https://world.openfoodfacts.net/api/v2/product/3017624010701?fields=product_name,ingredients,brands,allergens"

    fun getProduct(code: String): JsonFoodItem? {
        val retrofitBuilder =
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(FoodApiInterface::class.java)
        val retrofitData = retrofitBuilder.getData(code)

        return retrofitData.execute().body().also { Log.d("Respuesta",it.toString()) }


    }
}
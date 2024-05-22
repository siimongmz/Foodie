package com.example.foodie.api


import com.example.foodie.api.data.FoodItem
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FoodApi {
    companion object {
        private const val BASE_URL = "https://world.openfoodfacts.net/api/v2/"
    }

    fun getProduct(code: String): FoodItem? {
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
package com.example.foodie.facades

import com.example.foodie.api.FoodApi
import com.example.foodie.api.data.JsonFoodItem

class FoodApiFacade {

    fun getProduct(code:String): JsonFoodItem? {
        val foodApi = FoodApi()

        return foodApi.getProduct(code)

    }


}
package com.example.foodie.facades

import com.example.foodie.api.FoodApi
import com.example.foodie.api.data.FoodItem

class FoodApiFacade {
    private val foodApi = FoodApi()

    fun getProduct(code: String): FoodItem? {
        return foodApi.getProduct(code)

    }


}
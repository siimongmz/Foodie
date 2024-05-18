package com.example.foodie.facades

import com.example.foodie.api.FoodApi
import com.example.foodie.api.data.FoodItem

class FoodApiFacade {

    fun getProduct(code:String): FoodItem? {
        val foodApi = FoodApi()

        return foodApi.getProduct(code)

    }


}
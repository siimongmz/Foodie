package com.example.foodie.states

import com.example.foodie.api.data.FoodItem

data class SearchInfoState(
    var currentProduct: FoodItem? = null,
    var code: String? = null,
    var recentProducts: List<FoodItem> = emptyList(),
    var imageUrl: String? = null,
    var isDeleting:Boolean = false,
)
package com.example.foodie.events

import com.example.foodie.api.data.FoodItem

sealed interface SearchInfoEvent {
    data class CurrentProductChange(val foodItem: FoodItem?) : SearchInfoEvent
    data class CodeChange(val code: String?) : SearchInfoEvent
    data class AddRecentProduct(val foodItem: FoodItem?) : SearchInfoEvent
    data class RemoveRecentProduct(val foodItem: FoodItem?) : SearchInfoEvent
    data class ImageUrlChange(val url: String?) : SearchInfoEvent
    data object OpenAplication : SearchInfoEvent
    data object IsDeleting : SearchInfoEvent

}
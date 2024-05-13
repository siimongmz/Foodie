package com.example.foodie.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.foodie.api.data.JsonFoodItem
import java.util.Collections

class SearchInfoViewModel : ViewModel() {
    var currentProduct = mutableStateOf<JsonFoodItem?>(null)
    var code = mutableStateOf<String?>(null)
    var recentProducts = mutableStateOf<List<JsonFoodItem>>(Collections.emptyList<JsonFoodItem>())
}
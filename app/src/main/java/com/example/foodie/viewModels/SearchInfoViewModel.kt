package com.example.foodie.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.foodie.api.data.JsonFoodItem

class SearchInfoViewModel : ViewModel() {
    var currentProduct = mutableStateOf<JsonFoodItem?>(null)
    var code = mutableStateOf<String?>(null)
    var recentProducts = SnapshotStateList<JsonFoodItem>()
    var imagen = mutableStateOf<String?>(null)
}
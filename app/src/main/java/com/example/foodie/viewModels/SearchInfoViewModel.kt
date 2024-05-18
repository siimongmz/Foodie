package com.example.foodie.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.foodie.api.data.FoodItem

class SearchInfoViewModel : ViewModel() {
    var currentProduct = mutableStateOf<FoodItem?>(null)
    var code = mutableStateOf<String?>(null)
    var recentProducts = SnapshotStateList<FoodItem>()
    var imagen = mutableStateOf<String?>(null)
}
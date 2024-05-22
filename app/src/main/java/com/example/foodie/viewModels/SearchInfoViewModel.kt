package com.example.foodie.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodie.events.SearchInfoEvent
import com.example.foodie.persistance.FoodItemDao
import com.example.foodie.states.SearchInfoState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchInfoViewModel(private val dao: FoodItemDao) : ViewModel() {
    val state = MutableStateFlow(SearchInfoState())

    fun onEvent(event: SearchInfoEvent) {
        when (event) {
            SearchInfoEvent.OpenAplication -> {
                CoroutineScope(Dispatchers.IO).launch {
                    state.update {
                        it.copy(recentProducts = dao.getFoodItems())
                    }
                }
            }

            is SearchInfoEvent.CurrentProductChange -> {
                state.update { searchInfoState ->
                    searchInfoState.copy(currentProduct = event.foodItem)
                }
            }

            is SearchInfoEvent.ImageUrlChange -> {
                state.update { searchInfoState ->
                    searchInfoState.copy(imageUrl = event.url)
                }
            }

            is SearchInfoEvent.CodeChange -> {
                state.update { searchInfoState ->
                    searchInfoState.copy(code = event.code)
                }
            }

            is SearchInfoEvent.AddRecentProduct -> {
                if (!state.value.recentProducts.contains(event.foodItem)) {
                    state.update { searchInfoState ->
                        searchInfoState.copy(recentProducts = (searchInfoState.recentProducts + event.foodItem!!))
                    }
                    viewModelScope.launch {
                        dao.upsertFoodItem(foodItem = event.foodItem!!)
                    }
                }
            }

            is SearchInfoEvent.RemoveRecentProduct -> {
                state.update { searchInfoState ->
                    searchInfoState.copy(recentProducts = (searchInfoState.recentProducts.filterNot { it == event.foodItem }))
                }
                viewModelScope.launch {
                    dao.deleteFoodItem(foodItem = event.foodItem!!)
                }
            }
        }
    }

}
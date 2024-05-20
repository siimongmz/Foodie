package com.example.foodie.viewModels

import androidx.lifecycle.ViewModel
import com.example.foodie.events.SearchInfoEvent
import com.example.foodie.states.SearchInfoState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class SearchInfoViewModel : ViewModel() {
    val state = MutableStateFlow(SearchInfoState())

    fun onEvent(event: SearchInfoEvent) {
        when (event) {
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
                state.update { searchInfoState ->
                    searchInfoState.copy(recentProducts = (searchInfoState.recentProducts + event.foodItem!!))
                }
            }

            is SearchInfoEvent.RemoveRecentProduct -> {
                state.update { searchInfoState ->
                    searchInfoState.copy(recentProducts = (searchInfoState.recentProducts.filter { it.code != event.code }))
                }
            }
        }
    }

}
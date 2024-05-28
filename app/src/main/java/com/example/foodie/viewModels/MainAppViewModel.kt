package com.example.foodie.viewModels

import androidx.lifecycle.ViewModel
import com.example.foodie.events.MainAppEvent
import com.example.foodie.states.MainAppState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class MainAppViewModel() : ViewModel() {

    val state = MutableStateFlow(MainAppState())
    fun onEvent(event: MainAppEvent) {
        when (event) {
            is MainAppEvent.AllergenChange -> {
                state.update { mainAppState ->
                    mainAppState.copy(
                        allergens = mainAppState.allergens.also {
                            it[event.allergen.allergen.ordinal] =
                                !it[event.allergen.allergen.ordinal]
                        }
                    )
                }
            }
        }
    }


}
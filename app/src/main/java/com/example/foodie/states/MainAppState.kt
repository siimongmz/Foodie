package com.example.foodie.states

import androidx.compose.runtime.mutableStateListOf
import com.example.foodie.ALLERGENS_LIST

data class MainAppState(
    var allergens: MutableList<Boolean> = initAllergens()
) {
    companion object {
        fun initAllergens(): MutableList<Boolean> {
            val allergensList = mutableStateListOf<Boolean>()
            ALLERGENS_LIST.forEach { _ ->
                allergensList.add(element = false)
            }
            return allergensList
        }
    }

}
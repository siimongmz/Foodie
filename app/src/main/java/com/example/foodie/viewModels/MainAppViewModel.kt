package com.example.foodie.viewModels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.foodie.ALLERGENS_LIST
import com.example.foodie.ui.screens.SCREENS

class MainAppViewModel() : ViewModel() {

    companion object{
        fun initAllergens():MutableList<Boolean>{
            var allergensList = mutableStateListOf<Boolean>()
            ALLERGENS_LIST.forEach() {
                allergensList.add(false)
            }
            return allergensList
        }
    }

    var currentScreen = mutableStateOf(SCREENS.HOME)
    var allergens = initAllergens()


}
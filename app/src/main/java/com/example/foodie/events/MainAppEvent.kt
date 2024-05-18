package com.example.foodie.events

import com.example.foodie.Allergen

sealed interface MainAppEvent {
    data class AllergenChange(var allergen: Allergen) : MainAppEvent
}
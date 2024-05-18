package com.example.foodie.events

import com.example.foodie.configuration.Allergen

sealed interface MainAppEvent {
    data class AllergenChange(var allergen: Allergen) : MainAppEvent
}
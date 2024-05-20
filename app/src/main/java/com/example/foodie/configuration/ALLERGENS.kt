package com.example.foodie.configuration

enum class ALLERGENS {
    EGG,MILK,GLUTEN,SOYBEANS,NUTS,FISH
}
data class Allergen(val allergen: ALLERGENS, val name:String)
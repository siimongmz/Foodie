package com.example.foodie

enum class ALLERGENS {
    EGG,MILK,GLUTEN,SOYBEANS,NUTS
}
data class Allergen(val allergen: ALLERGENS, val name:String)
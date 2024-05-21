package com.example.foodie.util.tools

fun translate(text: String): String {
    return when (text) {
        "eggs" -> "Huevo"
        "milk" -> "Leche"
        "gluten" -> "Gluten"
        "soybeans" -> "Soja"
        "nuts" -> "Nueces"
        "fish" -> "Pescado"
        else -> "Sin Traduccion: $text"

    }

}
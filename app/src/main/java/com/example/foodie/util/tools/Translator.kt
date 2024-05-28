package com.example.foodie.util.tools

import android.content.Context
import com.example.foodie.R

fun translate(text: String): String {
    return when (text) {
        "eggs" -> "Huevo"
        "milk" -> "Leche"
        "gluten" -> "Gluten"
        "soybeans" -> "Soja"
        "nuts" -> "Nueces"
        "fish" -> "Pescado"
        else -> "'$text'"

    }
}

fun internationalize(context: Context, text: String):String{
    return when (text) {
        "Huevo" -> context.getString(R.string.eggs)
        "Leche" -> context.getString(R.string.milk)
        "Gluten" -> context.getString(R.string.gluten)
        "Soja" -> context.getString(R.string.soy)
        "Nueces" -> context.getString(R.string.nuts)
        "Pescado" -> context.getString(R.string.fish)
        else -> text

    }
}
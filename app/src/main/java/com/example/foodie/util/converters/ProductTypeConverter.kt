package com.example.foodie.util.converters

import androidx.room.TypeConverter
import com.example.foodie.api.data.Product
import com.google.gson.Gson

class ProductTypeConverter {
    @TypeConverter
    fun fromFoodItem(product: Product): String {
        return Gson().toJson(product)
    }

    @TypeConverter
    fun fromString(string: String): Product {
        return Gson().fromJson(string, Product::class.java)
    }

}
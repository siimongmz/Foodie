package com.example.foodie.api.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.foodie.util.converters.ProductTypeConverter
import com.google.gson.annotations.SerializedName

@Entity
@TypeConverters(ProductTypeConverter::class)
data class FoodItem(
    @PrimaryKey(false)
    val code: String,
    val product: Product?,
    val status: Int,
    @SerializedName("status_verbose")
    val statusVerbose: String
)
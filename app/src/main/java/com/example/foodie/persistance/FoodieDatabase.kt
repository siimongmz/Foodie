package com.example.foodie.persistance

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foodie.api.data.FoodItem
import com.example.foodie.util.converters.ProductTypeConverter

@Database(
    entities = [FoodItem::class],
    version = 1
)
@TypeConverters(ProductTypeConverter::class)
abstract class FoodieDatabase : RoomDatabase() {
    abstract val dao: FoodItemDao
}
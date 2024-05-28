package com.example.foodie.persistance

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.foodie.api.data.FoodItem

@Dao
interface FoodItemDao {
    @Upsert
    suspend fun upsertFoodItem(foodItem: FoodItem)

    @Delete
    suspend fun deleteFoodItem(foodItem: FoodItem)

    @Query("SELECT * FROM FoodItem")
    fun getFoodItems(): List<FoodItem>

    @Query("SELECT * FROM FoodItem WHERE code=:id ")
    fun getFoodItemById(id: String): LiveData<FoodItem>

}
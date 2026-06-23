package com.example.proteintracker

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomFoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomFood(food: CustomFoodEntry)

    @Delete
    suspend fun deleteCustomFood(food: CustomFoodEntry)

    @Query("SELECT * FROM custom_foods ORDER BY name ASC")
    fun getAllCustomFoods(): Flow<List<CustomFoodEntry>>
}
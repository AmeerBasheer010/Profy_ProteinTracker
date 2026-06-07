package com.example.proteintracker

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {

    @Insert
    suspend fun insertFood(food: FoodEntry)

    @Delete
    suspend fun deleteFood(food: FoodEntry)

    @Query("SELECT * FROM food_entries WHERE date = :date")
    fun getFoodsByDate(date: String): Flow<List<FoodEntry>>

    @Query("SELECT DISTINCT date FROM food_entries ORDER BY date DESC")
    fun getAllDates(): Flow<List<String>>
}
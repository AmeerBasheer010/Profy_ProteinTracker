package com.example.proteintracker

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_entries")
data class FoodEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val proteinPerPiece: Int,
    val unit: String,
    val quantity: Int,
    val date: String
)
package com.example.proteintracker

import android.content.Context

class UserPreferences(context: Context) {
    private val prefs = context.getSharedPreferences("profy_prefs", Context.MODE_PRIVATE)

    fun isFirstTime(): Boolean {
        return prefs.getBoolean("is_first_time", true)
    }

    fun saveUserData(name: String, goal: Int) {
        prefs.edit()
            .putString("user_name", name)
            .putInt("daily_goal", goal)
            .putBoolean("is_first_time", false)
            .apply()
    }

    fun getUserName(): String {
        return prefs.getString("user_name", "User") ?: "User"
    }

    fun getDailyGoal(): Int {
        return prefs.getInt("daily_goal", 120)
    }

    fun updateGoal(newGoal: Int) {
        prefs.edit().putInt("daily_goal", newGoal).apply()
    }
}
package com.example.foodrecipeapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.foodrecipeapp.pojo.Meal

@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(meal: Meal):Long

    @Delete
    suspend fun delete(meal:Meal)

    @Query("SELECT * FROM mealInformation")
    fun getAllMeals():LiveData<List<Meal>>
}
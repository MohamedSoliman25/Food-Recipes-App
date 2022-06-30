package com.example.foodrecipeapp.repository

import com.example.foodrecipeapp.db.MealDao
import com.example.foodrecipeapp.pojo.Meal
import com.example.foodrecipeapp.retrofit.MealApi
import javax.inject.Inject

class MealRepository @Inject constructor(private val mealApi:MealApi, private val mealDao: MealDao) {


    suspend fun getRandomMeal() = mealApi.getRandomMeal()
    suspend fun getMealDetails(id:String) = mealApi.getMealDetails(id)
   suspend fun getPopularItems(category:String) = mealApi.getPopularItems(category)
    suspend fun getCategories() = mealApi.getCategories()
   suspend fun getMealByCategories(category:String) = mealApi.getMealByCategories(category)
   suspend fun searchMeal(searchQuery:String) = mealApi.searchMeal(searchQuery)

   suspend fun insertMeal(meal: Meal) = mealDao.insert(meal)
    suspend fun deleteMeal(meal:Meal) = mealDao.delete(meal)
    fun getAllMeals() = mealDao.getAllMeals()

}
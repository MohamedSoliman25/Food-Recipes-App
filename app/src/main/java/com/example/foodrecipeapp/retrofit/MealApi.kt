package com.example.foodrecipeapp.retrofit

import com.example.foodrecipeapp.pojo.CategoryList
import com.example.foodrecipeapp.pojo.MealByCategoryList
import com.example.foodrecipeapp.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("lookup.php")
    fun getMealDetails(@Query("i") id:String):Call<MealList>

    @GET("filter.php")
    fun getPopularItems(@Query("c") category:String):Call<MealByCategoryList>

    @GET("categories.php")
    fun getCategories():Call<CategoryList>

    @GET("filter.php")
    fun getMealByCategories(@Query("c") category:String):Call<MealByCategoryList>
}
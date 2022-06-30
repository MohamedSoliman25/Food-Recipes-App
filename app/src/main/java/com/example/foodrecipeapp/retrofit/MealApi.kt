package com.example.foodrecipeapp.retrofit

import com.example.foodrecipeapp.pojo.CategoryList
import com.example.foodrecipeapp.pojo.MealByCategoryList
import com.example.foodrecipeapp.pojo.MealList
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET("random.php")
   suspend fun getRandomMeal(): Response<MealList>

    @GET("lookup.php")
  suspend fun getMealDetails(@Query("i") id:String):Response<MealList>

    @GET("filter.php")
   suspend fun getPopularItems(@Query("c") category:String):Response<MealByCategoryList>

    @GET("categories.php")
 suspend fun getCategories():Response<CategoryList>

    @GET("filter.php")
  suspend fun getMealByCategories(@Query("c") category:String):Response<MealByCategoryList>

    @GET("search.php")
  suspend fun searchMeal(@Query("s") searchQuery:String):Response<MealList>


}
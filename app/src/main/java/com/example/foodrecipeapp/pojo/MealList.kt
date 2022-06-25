package com.example.foodrecipeapp.pojo


import com.google.gson.annotations.SerializedName

data class MealList(
    @SerializedName("meals")
    var meals: List<Meal>?
)
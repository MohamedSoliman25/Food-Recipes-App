package com.example.foodrecipeapp.pojo


import com.google.gson.annotations.SerializedName

data class MealByCategory(
    @SerializedName("idMeal")
    var idMeal: String?,
    @SerializedName("strMeal")
    var strMeal: String?,
    @SerializedName("strMealThumb")
    var strMealThumb: String?
)
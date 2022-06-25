package com.example.foodrecipeapp.pojo


import com.google.gson.annotations.SerializedName

data class MealByCategoryList(
    @SerializedName("meals")
    var meals: List<MealByCateogory>?
)
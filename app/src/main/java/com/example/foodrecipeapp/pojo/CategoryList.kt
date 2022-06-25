package com.example.foodrecipeapp.pojo


import com.google.gson.annotations.SerializedName

data class CategoryList(
    @SerializedName("categories")
    var categories: List<Category>?
)
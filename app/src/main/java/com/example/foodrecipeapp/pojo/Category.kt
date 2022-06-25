package com.example.foodrecipeapp.pojo


import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("idCategory")
    var idCategory: String?,
    @SerializedName("strCategory")
    var strCategory: String?,
    @SerializedName("strCategoryDescription")
    var strCategoryDescription: String?,
    @SerializedName("strCategoryThumb")
    var strCategoryThumb: String?
)
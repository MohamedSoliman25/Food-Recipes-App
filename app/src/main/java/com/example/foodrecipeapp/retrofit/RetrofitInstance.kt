package com.example.foodrecipeapp.retrofit

import com.example.foodrecipeapp.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api:MealApi by lazy {
        Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MealApi::class.java)
    }
}
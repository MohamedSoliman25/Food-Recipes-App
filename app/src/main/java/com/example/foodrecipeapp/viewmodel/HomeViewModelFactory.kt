package com.example.foodrecipeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodrecipeapp.db.MealDatabase

class HomeViewModelFactory(
        val mealDatabase:MealDatabase
):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(mealDatabase) as T
    }

}
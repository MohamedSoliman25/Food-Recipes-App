package com.example.foodrecipeapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodrecipeapp.db.MealDatabase
import com.example.foodrecipeapp.pojo.*
import com.example.foodrecipeapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(
        private val mealDatabase: MealDatabase

): ViewModel() {
    private  val TAG = "HomeViewModel"

    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemsLiveData = MutableLiveData<List<MealByCateogory>>()
    private var categoriesLiveData = MutableLiveData<List<Category>>()

    init{
        getRandomMeal()
    }
    fun getRandomMeal(){
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    val randomMeal: Meal = response.body()!!.meals!![0]
                    randomMealLiveData.value = randomMeal
                }
                else {
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun observeRandomMealLiveData():LiveData<Meal>{
        return randomMealLiveData
    }


    fun getPopularItems(){
        RetrofitInstance.api.getPopularItems("seafood").enqueue(object : Callback<MealByCategoryList> {
            override fun onResponse(call: Call<MealByCategoryList>, response: Response<MealByCategoryList>) {
                if (response.body() != null) {
                    popularItemsLiveData.value = response.body()!!.meals

//                    Log.d(TAG, "onResponse: ${response.body()!!.meals}")
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<MealByCategoryList>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
    }
    fun observePopularItemsLiveData():LiveData<List<MealByCateogory>>{
        return popularItemsLiveData
    }

    fun getCategories(){
        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                response.body()?.let { categoryList ->
                    categoriesLiveData.postValue(categoryList.categories)

                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun observeCategoriesLiveData():LiveData<List<Category>>{
        return categoriesLiveData
    }

    fun observeFavoriteMealLiveData():LiveData<List<Meal>>{
        return mealDatabase.mealDao().getAllMeals()
    }

    fun deleteMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().delete(meal)
        }
    }

    fun insertMeal(meal:Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().insert(meal)
        }

    }

}
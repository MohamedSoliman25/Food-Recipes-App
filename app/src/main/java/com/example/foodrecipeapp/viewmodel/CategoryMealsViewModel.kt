package com.example.foodrecipeapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodrecipeapp.pojo.MealByCategoryList
import com.example.foodrecipeapp.pojo.MealByCateogory
import com.example.foodrecipeapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CategoryMealsViewModel : ViewModel(){

    private val TAG = "CategoryMealsViewModel"
    private var mealsLiveData: MutableLiveData<List<MealByCateogory>> = MutableLiveData<List<MealByCateogory>>()



     fun getMealsByCategory(categoryName:String){

        RetrofitInstance.api.getMealByCategories(categoryName).enqueue(object : Callback<MealByCategoryList> {
            override fun onResponse(call: Call<MealByCategoryList>, response: Response<MealByCategoryList>) {
                response.body()?.let { mealsList ->
                    mealsLiveData.postValue(mealsList.meals)

                }
            }

            override fun onFailure(call: Call<MealByCategoryList>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun observeCategories(): LiveData<List<MealByCateogory>> {
        return mealsLiveData
    }

}
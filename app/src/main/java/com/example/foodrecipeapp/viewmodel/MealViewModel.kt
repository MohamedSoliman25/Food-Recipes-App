package com.example.foodrecipeapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodrecipeapp.db.MealDatabase
import com.example.foodrecipeapp.pojo.Meal
import com.example.foodrecipeapp.pojo.MealList
import com.example.foodrecipeapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel (
      val  mealDatabase: MealDatabase
        ):ViewModel() {
    private val TAG = "MealViewModel"
    private var mealDetailsLiveData = MutableLiveData<Meal>()
    fun getMealDetail(id:String){
        RetrofitInstance.api.getMealDetails(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    mealDetailsLiveData.value = response.body()!!.meals!!.get(0)
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun observeMealDetailsLiveData(): LiveData<Meal> {
        return mealDetailsLiveData
    }

    fun insertMeal(meal:Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().insert(meal)
        }

    }


}
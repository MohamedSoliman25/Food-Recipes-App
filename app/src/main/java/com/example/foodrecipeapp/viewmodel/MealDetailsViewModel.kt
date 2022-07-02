package com.example.foodrecipeapp.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodrecipeapp.db.MealDatabase
import com.example.foodrecipeapp.pojo.Meal
import com.example.foodrecipeapp.pojo.MealList
import com.example.foodrecipeapp.repository.MealRepository
import com.example.foodrecipeapp.retrofit.RetrofitInstance
import com.example.foodrecipeapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MealDetailsViewModel @Inject constructor(
       val app:Application,
        private val mealRepository: MealRepository
        ):ViewModel() {
    private val TAG = "MealViewModel"
    private var _mealDetailsLiveData = MutableLiveData<Resource<Meal>>()
     val mealDetailsLiveData :LiveData<Resource<Meal>> get()= _mealDetailsLiveData


    fun getMealDetail(id:String)  = viewModelScope.launch {
//        _mealDetailsLiveData.postValue(Resource.Loading())
//        val response = mealRepository.getMealDetails(id)
//        _mealDetailsLiveData.postValue(handleMealDetailsResponse(response))
        safeMealDetailsCall(id)
    }



    private fun handleMealDetailsResponse(response: Response<MealList>): Resource<Meal>? {

        if (response.isSuccessful){
            response.body()?.let { mealList ->
                return Resource.Success(mealList.meals?.get(0)!!)
            }
        }
        return Resource.Error(response.message())
    }

    private suspend fun safeMealDetailsCall(id: String) {

        _mealDetailsLiveData.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()){
                val response = mealRepository.getMealDetails(id)
                _mealDetailsLiveData.postValue(handleMealDetailsResponse(response))
            }
            else{
                _mealDetailsLiveData.postValue(Resource.Error("No Internet connection"))
            }

        }catch (t:Throwable){
            when(t){
                is IOException ->_mealDetailsLiveData.postValue(Resource.Error("Network Failure"))
                else -> _mealDetailsLiveData.postValue(Resource.Error("Conversion Error"))
            }
        }
    }


    fun insertMeal(meal:Meal) = viewModelScope.launch {
                mealRepository.insertMeal(meal)
    }

    private fun hasInternetConnection():Boolean{
        val connectivityManager =app.getSystemService(Context.CONNECTIVITY_SERVICE
        )as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
                    ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true

                else -> false
            }
        }else{
            val activeNetworkInfo =  connectivityManager.activeNetworkInfo

            if(activeNetworkInfo!=null && activeNetworkInfo.isConnected){
                return true
            }
        }
        return false

    }


}
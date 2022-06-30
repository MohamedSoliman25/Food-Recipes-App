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
import com.example.foodrecipeapp.pojo.MealByCategoryList
import com.example.foodrecipeapp.pojo.MealByCategory
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
class CategoryMealsViewModel @Inject constructor(
        val app: Application,
        private val mealRepository: MealRepository
) : ViewModel(){

    private val TAG = "CategoryMealsViewModel"
    private var _mealsLiveData =  MutableLiveData<Resource<List<MealByCategory>>>()
      val mealsLiveData :LiveData<Resource<List<MealByCategory>>> get() = _mealsLiveData



     fun getMealsByCategory(categoryName:String)  = viewModelScope.launch {
//         _mealsLiveData.postValue(Resource.Loading())
//         val response = mealRepository.getMealByCategories(categoryName)
//         _mealsLiveData.postValue(handleMealsByCategoryResponse(response))
         safeMealsByCategory(categoryName)
     }



    private fun handleMealsByCategoryResponse(response: Response<MealByCategoryList>): Resource<List<MealByCategory>>? {

        if (response.isSuccessful){
            response.body()?.let { mealByCategoryList ->
                return Resource.Success(mealByCategoryList.meals!!)
            }
        }
        return Resource.Error(response.message())
    }

    private suspend fun safeMealsByCategory(categoryName: String) {

        _mealsLiveData.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()){
                val response = mealRepository.getMealByCategories(categoryName)
                _mealsLiveData.postValue(handleMealsByCategoryResponse(response))
            }
            else{
                _mealsLiveData.postValue(Resource.Error("No Internet connection"))
            }

        }catch (t:Throwable){
            when(t){
                is IOException ->_mealsLiveData.postValue(Resource.Error("Network Failure"))
                else -> _mealsLiveData.postValue(Resource.Error("Conversion Error"))
            }
        }
    }


    fun hasInternetConnection():Boolean{
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
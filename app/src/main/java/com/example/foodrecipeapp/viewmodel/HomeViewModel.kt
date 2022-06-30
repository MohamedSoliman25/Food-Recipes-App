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
import com.example.foodrecipeapp.pojo.*
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
class HomeViewModel @Inject constructor(
        val app:Application,
        private val mealRepository: MealRepository

): ViewModel() {
    private  val TAG = "HomeViewModel"

    private var _randomMealLiveData = MutableLiveData<Resource<Meal>>()
     val randomMealLiveData:LiveData<Resource<Meal>> get() = _randomMealLiveData

    private var _popularItemsLiveData = MutableLiveData<Resource<List<MealByCategory>>>()
     val popularItemsLiveData :LiveData<Resource<List<MealByCategory>>> get() = _popularItemsLiveData

    private var _categoriesLiveData = MutableLiveData<Resource<List<Category>>>()
     val categoriesLiveData :LiveData<Resource<List<Category>>> get() = _categoriesLiveData

    private var _searchLiveData = MutableLiveData<Resource<List<Meal>>>()
     val searchLiveData:LiveData<Resource<List<Meal>>> get() = _searchLiveData



    init{
        getRandomMeal()
    }
     fun getRandomMeal() = viewModelScope.launch {
//    _randomMealLiveData.postValue(Resource.Loading())
//    val response = mealRepository.getRandomMeal()
//    _randomMealLiveData.postValue(handleRandomMealResponse(response))
         safeRandomMealCall()
     }


    private fun handleRandomMealResponse(response: Response<MealList>) :Resource<Meal>{

        if (response.isSuccessful){
            response.body()?.let { mealList ->
                return Resource.Success(mealList.meals?.get(0)!!)
            }
        }
        return Resource.Error(response.message())
    }

    private suspend fun safeRandomMealCall() {

        _randomMealLiveData.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()){
                val response = mealRepository.getRandomMeal()
                _randomMealLiveData.postValue(handleRandomMealResponse(response))
            }
            else{
                _randomMealLiveData.postValue(Resource.Error("No Internet connection"))
            }

        }catch (t:Throwable){
            when(t){
                is IOException ->_randomMealLiveData.postValue(Resource.Error("Network Failure"))
                else -> _randomMealLiveData.postValue(Resource.Error("Conversion Error"))
            }
        }
    }


    fun getPopularItems(category:String)  = viewModelScope.launch {
//        _popularItemsLiveData.postValue(Resource.Loading())
//        val response = mealRepository.getPopularItems(category)
//        _popularItemsLiveData.postValue(handlePopularItemMealResponse(response))
        safePopularItemsCall(category)
    }



    private fun handlePopularItemMealResponse(response: Response<MealByCategoryList>): Resource<List<MealByCategory>>? {

        if (response.isSuccessful){
            response.body()?.let { mealByCategoryList ->
                return Resource.Success(mealByCategoryList.meals!!)
            }
        }
        return Resource.Error(response.message())
    }

    private suspend fun safePopularItemsCall(category: String) {

        _popularItemsLiveData.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()){
                val response = mealRepository.getPopularItems(category)
                _popularItemsLiveData.postValue(handlePopularItemMealResponse(response))
            }
            else{
                _popularItemsLiveData.postValue(Resource.Error("No Internet connection"))
            }

        }catch (t:Throwable){
            when(t){
                is IOException ->_popularItemsLiveData.postValue(Resource.Error("Network Failure"))
                else -> _popularItemsLiveData.postValue(Resource.Error("Conversion Error"))
            }
        }
    }



    fun getCategories() = viewModelScope.launch {
//        _categoriesLiveData.postValue(Resource.Loading())
//        val response = mealRepository.getCategories()
//        _categoriesLiveData.postValue(handleCategoriesResponse(response))
        safeCategoriesCall()
    }



    private fun handleCategoriesResponse(response: Response<CategoryList>): Resource<List<Category>>? {

        if (response.isSuccessful){
            response.body()?.let { categoryList ->
                return Resource.Success(categoryList.categories!!)
            }
        }
        return Resource.Error(response.message())
    }

    private suspend fun safeCategoriesCall() {
        
        _categoriesLiveData.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()){
                val response = mealRepository.getCategories()
                _categoriesLiveData.postValue(handleCategoriesResponse(response))
            }
            else{
                _categoriesLiveData.postValue(Resource.Error("No Internet connection"))
            }

        }catch (t:Throwable){
            when(t){
                is IOException ->_categoriesLiveData.postValue(Resource.Error("Network Failure"))
                else -> _categoriesLiveData.postValue(Resource.Error("Conversion Error"))
            }
        }
    }


    fun searchMeal(searchQuery:String)  = viewModelScope.launch {
//        _searchLiveData.postValue(Resource.Loading())
//        val response = mealRepository.searchMeal(searchQuery)
//        _searchLiveData.postValue(handleSearchMealResponse(response))
        safeSearchMealCall(searchQuery)
    }



    private fun handleSearchMealResponse(response: Response<MealList>): Resource<List<Meal>>? {

        if (response.isSuccessful){
            response.body()?.let { mealList ->
                return Resource.Success(mealList.meals!!)
            }
        }
        return Resource.Error(response.message())
    }

    private suspend fun safeSearchMealCall(searchQuery: String) {

        _searchLiveData.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()){
                val response = mealRepository.searchMeal(searchQuery)
                _searchLiveData.postValue(handleSearchMealResponse(response))
            }
            else{
                _searchLiveData.postValue(Resource.Error("No Internet connection"))
            }

        }catch (t:Throwable){
            when(t){
                is IOException ->_searchLiveData.postValue(Resource.Error("Network Failure"))
                else -> _searchLiveData.postValue(Resource.Error("Conversion Error"))
            }
        }
    }


    //  i will also use it for undo click
        fun insertMeal(meal:Meal) = viewModelScope.launch {
            mealRepository.insertMeal(meal)
        }


        fun deleteMeal(meal: Meal) = viewModelScope.launch {
              mealRepository.deleteMeal(meal)
            }


        fun observeFavoriteMealLiveData():LiveData<List<Meal>> = mealRepository.getAllMeals()


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
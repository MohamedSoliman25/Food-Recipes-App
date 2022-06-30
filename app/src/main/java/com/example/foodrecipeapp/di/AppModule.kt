package com.example.foodrecipeapp.di

import android.content.Context
import com.example.foodrecipeapp.db.MealDao
import com.example.foodrecipeapp.db.MealDatabase
import com.example.foodrecipeapp.retrofit.MealApi
import com.example.foodrecipeapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideMealApi():MealApi{
        return   Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MealApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMealDatabase(@ApplicationContext context: Context):MealDatabase{
        return MealDatabase.getInstance(context)
    }
    @Provides
    @Singleton
    fun provideMealDao(mealDatabase:MealDatabase):MealDao{
        return mealDatabase.mealDao()
    }

}
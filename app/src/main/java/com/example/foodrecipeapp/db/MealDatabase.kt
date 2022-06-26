package com.example.foodrecipeapp.db

import android.content.Context
import androidx.room.*
import com.example.foodrecipeapp.pojo.Meal

@Database(entities = [Meal::class],version = 1)
@TypeConverters(MealTypeConverter::class)
abstract class MealDatabase : RoomDatabase() {
    abstract fun mealDao(): MealDao

    companion object {
        @Volatile
        private var INSTANCE: MealDatabase? = null

        @Synchronized
        fun getInstance(context: Context): MealDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        MealDatabase::class.java,
                        "meal_dp"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}

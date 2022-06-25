package com.example.foodrecipeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodrecipeapp.adapters.CategoryMealsAdapter
import com.example.foodrecipeapp.databinding.ActivityCategoryMealsBinding
import com.example.foodrecipeapp.fragments.HomeFragment
import com.example.foodrecipeapp.viewmodel.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {
   private lateinit var binding:ActivityCategoryMealsBinding
   lateinit var categoryMealsViewModel:CategoryMealsViewModel
   lateinit var categoryMealsAdapter: CategoryMealsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      binding =  ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecyclerView()
        categoryMealsViewModel = ViewModelProvider(this)[CategoryMealsViewModel::class.java]
        categoryMealsViewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)
        categoryMealsViewModel.observeCategories().observe(this,{mealsList->
                categoryMealsAdapter.setMealList(mealsList)
        })
    }
    private fun prepareRecyclerView(){
        categoryMealsAdapter = CategoryMealsAdapter()

        binding.mealRecyclerview.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = categoryMealsAdapter

        }
    }
}
package com.example.foodrecipeapp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodrecipeapp.CategoryMealsActivity
import com.example.foodrecipeapp.MainActivity
import com.example.foodrecipeapp.MealActivity
import com.example.foodrecipeapp.adapters.CategoriesRecyclerAdapter
import com.example.foodrecipeapp.adapters.MostPopularRecyclerAdapter
import com.example.foodrecipeapp.databinding.FragmentHomeBinding
import com.example.foodrecipeapp.pojo.Meal
import com.example.foodrecipeapp.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private val TAG = "HomeFragment"
    private lateinit var binding :FragmentHomeBinding
    private lateinit var viewModel:HomeViewModel
    private  lateinit var randomMeal:Meal
    private lateinit var popularItemsAdapter:MostPopularRecyclerAdapter
    private lateinit var categoriesAdapter: CategoriesRecyclerAdapter



    companion object{
        const val MEAL_ID = "com.example.foodrecipeapp.fragments.idMeal"
        const val MEAL_NAME = "com.example.foodrecipeapp.fragments.nameMeal"
        const val MEAL_THUMB = "com.example.foodrecipeapp.fragments.thumbMeal"
        const val CATEGORY_NAME = "com.example.foodrecipeapp.fragments.categoryName"

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        popularItemsAdapter = MostPopularRecyclerAdapter()
        categoriesAdapter = CategoriesRecyclerAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        viewModel.getRandomMeal()
        observerRandomMeal()
//        Log.d(TAG, "observe2: ${randomMeal.idMeal} , ${randomMeal.strMeal} , ${randomMeal.strMealThumb}")

        onRandomMealClick()
        // popular items
        popularItemsRecyclerView()
        viewModel.getPopularItems()
        observePopularItemsLiveData()
        onPopularItemClick()

        // category List
        categoryItemsRecyclerView()
        viewModel.getCategories()
        observeCategoriesLiveData()
        onCategoryItemClick()

    }

    private fun onCategoryItemClick() {
        categoriesAdapter.onItemClick ={category->
            val intent  = Intent(activity,CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME,category.strCategory)
            startActivity(intent)
        }
    }

    private fun categoryItemsRecyclerView() {
        binding.recyclerViewCategories.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter = categoriesAdapter
        }
    }

    private fun observeCategoriesLiveData() {
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner, { categories ->
//            categories.forEach { category ->
//                Log.d(TAG, "test: ${category.strCategory}")
//            }
            categoriesAdapter.setCategoryList(categories)
        })
    }

    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClick ={meal->
            val intent  = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun popularItemsRecyclerView() {
        binding.recViewMealsPopular.apply{
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter = popularItemsAdapter
        }
    }

    private fun observePopularItemsLiveData() {
        viewModel.observePopularItemsLiveData().observe(viewLifecycleOwner,{ mealList->

            popularItemsAdapter.setMealList(mealList)

        })
    }

    private fun onRandomMealClick(){
        binding.randomMeal.setOnClickListener{
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)

            startActivity(intent)
        }
    }
    private fun observerRandomMeal(){
        viewModel.observeRandomMealLiveData().observe(viewLifecycleOwner, Observer{ meal->
            Glide.with(this@HomeFragment)
                    .load(meal.strMealThumb)
                    .into(binding.imgRandomMeal)
            this.randomMeal = meal
            Log.d(TAG, "observe: ${randomMeal.idMeal} , ${randomMeal.strMeal} , ${randomMeal.strMealThumb}")

        })
    }

}
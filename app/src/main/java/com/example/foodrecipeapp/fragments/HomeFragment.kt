package com.example.foodrecipeapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodrecipeapp.MainActivity
import com.example.foodrecipeapp.adapters.CategoriesRecyclerAdapter
import com.example.foodrecipeapp.adapters.MostPopularRecyclerAdapter
import com.example.foodrecipeapp.databinding.FragmentHomeBinding
import com.example.foodrecipeapp.pojo.Meal
import com.example.foodrecipeapp.util.Resource
import com.example.foodrecipeapp.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private val TAG = "HomeFragment"
    private lateinit var binding :FragmentHomeBinding
    private lateinit var viewModel:HomeViewModel
    private  lateinit var randomMeal:Meal
    private lateinit var popularItemsAdapter:MostPopularRecyclerAdapter
    private lateinit var categoriesAdapter: CategoriesRecyclerAdapter
   private  var isSuccess = false



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
//        viewModel.getPopularItems()
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
//            val intent  = Intent(activity,CategoryMealsActivity::class.java)
//            intent.putExtra(CATEGORY_NAME,category.strCategory)
//            startActivity(intent)
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToCategoryMealsFragment(category.strCategory!!)
            )
        }
    }

    private fun categoryItemsRecyclerView() {
        binding.recyclerViewCategories.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter = categoriesAdapter
        }
    }

    private fun observeCategoriesLiveData() {
        viewModel.categoriesLiveData.observe(viewLifecycleOwner, { response ->

            when(response){
                is Resource.Success->{
                    hideProgressBarForCategory()
                    response.data?.let { categories->
                        //            categories.forEach { category ->
//                Log.d(TAG, "test: ${category.strCategory}")
//            }
                        categoriesAdapter.setCategoryList(categories)
                    }
                }
                is Resource.Error->{
                    hideProgressBarForCategory()
                    response.message?.let {message->
                        Toast.makeText(activity,"$message", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBarForCategory()
                }
            }

        })
    }

    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClick ={meal->
//            val intent  = Intent(activity,MealActivity::class.java)
//            intent.putExtra(MEAL_ID,meal.idMeal)
//            intent.putExtra(MEAL_NAME,meal.strMeal)
//            intent.putExtra(MEAL_THUMB,meal.strMealThumb)
//            startActivity(intent)
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(meal.idMeal!!,meal.strMeal!!,meal.strMealThumb!!)
            )

        }
    }

    private fun popularItemsRecyclerView() {
        binding.recViewMealsPopular.apply{
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter = popularItemsAdapter
        }
    }

    private fun observePopularItemsLiveData() {
        viewModel.popularItemsLiveData.observe(viewLifecycleOwner,{ response->

            when (response) {
                is Resource.Success -> {
                    hideProgressBarForMostPopular()
                    response.data?.let { mealList->

                        popularItemsAdapter.setMealList(mealList)

                    }
                }
                is Resource.Error -> {
                    hideProgressBarForMostPopular()
                    response.message?.let { message ->
//                        Log.e(TAG, "An error occured: $message " )
                        Toast.makeText(activity,"$message", Toast.LENGTH_LONG).show()

                    }
                }
                is Resource.Loading-> {
                    showProgressBarForMostPopular()
                }
            }

        })
    }

    private fun onRandomMealClick(){
        binding.randomMeal.setOnClickListener {

            if (isSuccess) {
                findNavController().navigate(
                        HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(randomMeal.idMeal, randomMeal.strMeal!!, randomMeal.strMealThumb!!)
                )
            }
        }

    }
    private fun observerRandomMeal(){
        viewModel.randomMealLiveData.observe(viewLifecycleOwner, Observer{response->

            when (response) {
                is Resource.Success -> {
                    isSuccess = true
                    hideProgressBarForRandomMeal()
                    response.data?.let { meal ->
//                        newsAdapter.differ.submitList(newsResponse.articles?.toList())

                        Glide.with(this@HomeFragment)
                                .load(meal.strMealThumb)
                                .into(binding.imgRandomMeal)
                        this.randomMeal = meal
                        Log.d(TAG, "observe: ${randomMeal.idMeal} , ${randomMeal.strMeal} , ${randomMeal.strMealThumb}")

                        // i put this method inside observerRandomMeal because i want to display most popular items according category of random meal
                        viewModel.getPopularItems(this.randomMeal.strCategory!!)
                    }
                }
                is Resource.Error -> {
                    hideProgressBarForRandomMeal()
                    response.message?.let { message ->
                        Log.e(TAG, "An error occured: $message " )
                        Toast.makeText(activity,"$message", Toast.LENGTH_LONG).show()

                    }
                }
                is Resource.Loading-> {
                    showProgressBarForRandomMeal()
                }
            }



        })
    }

    private fun hideProgressBarForRandomMeal() {
        binding.pogressBarForRandomMeal.visibility = View.INVISIBLE
    }

    private fun showProgressBarForRandomMeal() {
        binding.pogressBarForRandomMeal.visibility = View.VISIBLE
    }

    private fun hideProgressBarForMostPopular() {
        binding.progressBarForMostPopular.visibility = View.INVISIBLE
    }

    private fun showProgressBarForMostPopular() {
        binding.progressBarForMostPopular.visibility = View.VISIBLE
    }

    private fun hideProgressBarForCategory() {
        binding.progressBarForCategory.visibility = View.INVISIBLE
    }

    private fun showProgressBarForCategory() {
        binding.progressBarForCategory.visibility = View.VISIBLE
    }
}
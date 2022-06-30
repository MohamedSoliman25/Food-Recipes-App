package com.example.foodrecipeapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodrecipeapp.adapters.CategoryMealsAdapter
import com.example.foodrecipeapp.databinding.FragmentCategoryMealsBinding
import com.example.foodrecipeapp.pojo.MealByCategory
import com.example.foodrecipeapp.util.Resource
import com.example.foodrecipeapp.viewmodel.CategoryMealsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryMealsFragment : Fragment() {

    private val  categoryMealsViewModel: CategoryMealsViewModel by viewModels()
    lateinit var categoryMealsAdapter: CategoryMealsAdapter
    private val args:CategoryMealsFragmentArgs by navArgs()


    private lateinit var binding : FragmentCategoryMealsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        categoryMealsViewModel = ViewModelProvider(this)[CategoryMealsViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryMealsBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        categoryMealsViewModel.getMealsByCategory(args.strCategory)
        observeMealByCategory()
        onCategoryItemClick()
    }


    private fun prepareRecyclerView(){
        categoryMealsAdapter = CategoryMealsAdapter()

        binding.mealRecyclerview.apply {
            layoutManager = GridLayoutManager(context,2, GridLayoutManager.VERTICAL,false)
            adapter = categoryMealsAdapter

        }
    }

    private fun observeMealByCategory(){

        categoryMealsViewModel.mealsLiveData.observe(viewLifecycleOwner,{response->

            when(response){
                is Resource.Success-> {
                    hideProgressBarForCategorMeals()
                    response.data?.let { mealsList ->

                        categoryMealsAdapter.setMealList(mealsList)

                    }
                }
                is Resource.Error->{
                    hideProgressBarForCategorMeals()
                    response.message?.let {message->
                        Toast.makeText(activity,"$message", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBarForCategoryMeals()
                }
            }

        })
    }
    private fun onCategoryItemClick() {
        categoryMealsAdapter.onItemClicked(object : CategoryMealsAdapter.OnItemCategoryClicked{
            override fun onClickListener(category: MealByCategory) {
                findNavController().navigate(
                    CategoryMealsFragmentDirections.actionCategoryMealsFragmentToMealDetailsFragment(category.idMeal!!,category.strMeal!!,category.strMealThumb!!)
                )
            }

        }
        )
    }

    private fun hideProgressBarForCategorMeals() {
        binding.progressBarForCategoryMeals.visibility = View.INVISIBLE
    }

    private fun showProgressBarForCategoryMeals() {
        binding.progressBarForCategoryMeals.visibility = View.VISIBLE
    }
}
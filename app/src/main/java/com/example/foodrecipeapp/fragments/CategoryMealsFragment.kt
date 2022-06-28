package com.example.foodrecipeapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodrecipeapp.adapters.CategoryMealsAdapter
import com.example.foodrecipeapp.databinding.FragmentCategoryMealsBinding
import com.example.foodrecipeapp.pojo.MealByCategory
import com.example.foodrecipeapp.viewmodel.CategoryMealsViewModel


class CategoryMealsFragment : Fragment() {

    lateinit var categoryMealsViewModel: CategoryMealsViewModel
    lateinit var categoryMealsAdapter: CategoryMealsAdapter
    private val args:CategoryMealsFragmentArgs by navArgs()


    private lateinit var binding : FragmentCategoryMealsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        categoryMealsViewModel = ViewModelProvider(this)[CategoryMealsViewModel::class.java]
        categoryMealsViewModel.getMealsByCategory(args.strCategory)
        categoryMealsViewModel.observeCategories().observe(viewLifecycleOwner,{mealsList->
            categoryMealsAdapter.setMealList(mealsList)
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
}
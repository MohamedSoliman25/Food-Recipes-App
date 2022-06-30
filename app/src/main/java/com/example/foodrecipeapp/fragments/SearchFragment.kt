package com.example.foodrecipeapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.foodrecipeapp.databinding.FragmentSearchBinding
import kotlinx.coroutines.Job
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodrecipeapp.MainActivity
import com.example.foodrecipeapp.adapters.MealsAdapter
import com.example.foodrecipeapp.pojo.Meal
import com.example.foodrecipeapp.util.Constants
import com.example.foodrecipeapp.util.Resource
import com.example.foodrecipeapp.viewmodel.HomeViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {

    private lateinit var fragmentSearchBinding:FragmentSearchBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var searchAdapter: MealsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentSearchBinding = FragmentSearchBinding.inflate(inflater,container,false)
        return fragmentSearchBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var job: Job? = null
        fragmentSearchBinding.etSearch.addTextChangedListener{editable->
            job?.cancel()
//            favoritesAdapter.differ.currentList.clear
            job = MainScope().launch {
                delay(Constants.SEARCH_TIME_DELAY)
                editable?.let {
//                    if(!editable.toString().trim().isEmpty()){
                    if(editable.toString().isNotEmpty()){
                        viewModel.searchMeal(editable.toString())

                    }
                }
            }
        }
        prepareRecyclerView()
        observeFavorites()
        onMealItemClick()

    }

    private fun prepareRecyclerView() {
        searchAdapter = MealsAdapter()
        fragmentSearchBinding.searchRecView.apply {
//            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
//            layoutManager = LinearLayoutManager(activity)

//            setHasFixedSize(false)
            adapter = searchAdapter
        }
    }

    private fun observeFavorites() {
        // warning : you should use require activity instead of viewlifeCycleOwner (because cannot add the same observer with different lifeCycles)
        viewModel.searchLiveData.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBarForSearch()
                    response.data?.let { mealList->

                        //            mealList.forEach {
//                Log.d(TAG, "observeFavorites: ${it.idMeal}")
//            }
                        searchAdapter.differ.submitList(mealList)

                    }
                }
                is Resource.Error -> {
                    hideProgressBarForSearch()
                    response.message?.let { message ->
//                        Log.e(TAG, "An error occured: $message " )
                        Toast.makeText(activity,"$message", Toast.LENGTH_LONG).show()

                    }
                }
                is Resource.Loading-> {
                    showProgressBarForSearch()
                }
            }

        })
    }

    private fun onMealItemClick() {
        searchAdapter.setOnMealClickListener(object :MealsAdapter.OnMealClickListener{
            override fun onMealClick(meal: Meal) {
                findNavController().navigate(
                    SearchFragmentDirections.actionSearchFragmentToMealDetailsFragment(meal.idMeal,meal.strMeal!!,meal.strMealThumb!!)
                )
            }

        }
        )
    }

    private fun hideProgressBarForSearch() {
        fragmentSearchBinding.progressBarForSearch.visibility = View.INVISIBLE
    }

    private fun showProgressBarForSearch() {
        fragmentSearchBinding.progressBarForSearch.visibility = View.VISIBLE
    }
}
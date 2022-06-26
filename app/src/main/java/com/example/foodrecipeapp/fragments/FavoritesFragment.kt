package com.example.foodrecipeapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipeapp.MainActivity
import com.example.foodrecipeapp.R
import com.example.foodrecipeapp.adapters.FavoriteMealsAdapter
import com.example.foodrecipeapp.databinding.FragmentFavoritesBinding
import com.example.foodrecipeapp.viewmodel.HomeViewModel
import com.google.android.material.snackbar.Snackbar


class FavoritesFragment : Fragment() {
    private lateinit var binding:FragmentFavoritesBinding
    private lateinit var viewModel:HomeViewModel
    private lateinit var favoritesAdapter:FavoriteMealsAdapter
    private  val TAG = "FavoritesFragment"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
     binding = FragmentFavoritesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeFavorites()

        val itemTouchHelper = object:ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or  ItemTouchHelper.DOWN,
                ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position  = viewHolder.adapterPosition
                val favMeal = favoritesAdapter.differ.currentList[position]
                viewModel.deleteMeal(favMeal)
                Snackbar.make(requireView(),"Meal deleted",Snackbar.LENGTH_LONG).setAction(
                        "Undo",
                        View.OnClickListener {
                            viewModel.insertMeal(favMeal)
                        }
                ).show()
            }

        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.favRecView)
    }

    private fun prepareRecyclerView() {
        favoritesAdapter = FavoriteMealsAdapter()
        binding.favRecView.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
//            setHasFixedSize(false)
            adapter = favoritesAdapter
        }
    }

    private fun observeFavorites() {
        // warning : you should use require activity instead of viewlifeCycleOwner (because cannot add the same observer with different lifeCycles)
        viewModel.observeFavoriteMealLiveData().observe(viewLifecycleOwner, { mealList ->
//            mealList.forEach {
//                Log.d(TAG, "observeFavorites: ${it.idMeal}")
//            }
            favoritesAdapter.differ.submitList(mealList)
        })
    }


}
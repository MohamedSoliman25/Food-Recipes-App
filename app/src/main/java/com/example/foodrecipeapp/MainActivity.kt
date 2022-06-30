package com.example.foodrecipeapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.foodrecipeapp.databinding.ActivityMainBinding
import com.example.foodrecipeapp.viewmodel.HomeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

//    val viewModel :HomeViewModel by lazy {
//        val mealDatabase = MealDatabase.getInstance(this)
//        val homeViewModelProviderFactory = HomeViewModelFactory(mealDatabase)
//       ViewModelProvider(this,homeViewModelProviderFactory).get(HomeViewModel::class.java)
//
//    }
    val viewModel:HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navController = Navigation.findNavController(this,R.id.frag_host)
        NavigationUI.setupWithNavController(bottomNavigation,navController)

    }
}
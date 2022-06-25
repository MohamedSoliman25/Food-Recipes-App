package com.example.foodrecipeapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.foodrecipeapp.databinding.ActivityMealBinding
import com.example.foodrecipeapp.fragments.HomeFragment
import com.example.foodrecipeapp.viewmodel.HomeViewModel
import com.example.foodrecipeapp.viewmodel.MealViewModel

class MealActivity : AppCompatActivity() {
    private val TAG = "MealActivity"
    private lateinit var binding:ActivityMealBinding
    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var youtubeLink:String
    private lateinit var mealMVVM:MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        mealMVVM = ViewModelProvider(this).get(MealViewModel::class.java)
        mealMVVM = ViewModelProvider(this)[MealViewModel::class.java]


        getMealInformationFromIntent()
        setInformationInViews()

        loadingCase()
        mealMVVM.getMealDetail(mealId)
        observerMealDetailsLiveData()

        onYouTubeImageClick()

    }

    private fun onYouTubeImageClick() {
        binding.imgYoutube.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private fun observerMealDetailsLiveData() {
        mealMVVM.observeMealDetailsLiveData().observe(this,{meal->
            onResponseCase()
            binding.tvCategoryInfo.text = "Category : ${meal.strCategory}"
            binding.tvAreaInfo.text  = "Area : ${meal.strArea}"
            binding.tvContent.text  = meal.strInstructions
            youtubeLink = meal.strYoutube!!
        })
    }

    private fun setInformationInViews() {
        Glide.with(applicationContext)
                .load(mealThumb)
                .into(binding.imgMealDetail)
        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInformationFromIntent(){
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
//        Log.d(TAG, "getMealInformationFromIntent: $mealId")
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
//        Log.d(TAG, "observe2: ${mealId} , ${mealName} , ${mealThumb}")

    }

    private fun loadingCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.btnSave.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.tvCategoryInfo.visibility = View.INVISIBLE
        binding.tvAreaInfo.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE

    }

    private fun onResponseCase(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnSave.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tvCategoryInfo.visibility = View.VISIBLE
        binding.tvAreaInfo.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
    }
}
package com.example.foodrecipeapp.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.foodrecipeapp.R
import com.example.foodrecipeapp.databinding.FragmentMealDetailsBinding
import com.example.foodrecipeapp.pojo.Meal
import com.example.foodrecipeapp.util.Resource
import com.example.foodrecipeapp.viewmodel.MealDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MealDetailsFragment : Fragment() {

    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var youtubeLink:String
    private var isSuccess = false

    private val mealDetailsMVVM: MealDetailsViewModel by viewModels()

    private var mealToSave: Meal? = null
    private val args:MealDetailsFragmentArgs by navArgs()

    private lateinit var binding:FragmentMealDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val mealDatabase = MealDatabase.getInstance(requireActivity())
//        val viewModelFactory = MealViewModelFactory(mealDatabase)
//        mealMVVM = ViewModelProvider(this,viewModelFactory)[MealViewModel::class.java]

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentMealDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getMealInformationFromArgs()
        setInformationInViews()

//        loadingCase()
        mealDetailsMVVM.getMealDetail(mealId)
        observerMealDetailsLiveData()

        onYouTubeImageClick()

        onFavoriteClick()

    }
    private fun onFavoriteClick() {
        binding.btnSave.setOnClickListener{
            mealToSave?.let {
                mealDetailsMVVM.insertMeal(it)
                Toast.makeText(requireActivity(), "Meal Saved", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun onYouTubeImageClick() {
        binding.imgYoutube.setOnClickListener{
            if (isSuccess){
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
            }
        }
    }

    private fun observerMealDetailsLiveData() {
        mealDetailsMVVM.mealDetailsLiveData.observe(viewLifecycleOwner,{ response->

            when (response) {
                is Resource.Success -> {
                    isSuccess = true
                    hideProgressBarForMealDetails()
                    response.data?.let { meal->

                        // onResponseCase()
                        mealToSave = meal
                        binding.tvCategoryInfo.text = "Category : ${meal.strCategory}"
                        binding.tvAreaInfo.text  = "Area : ${meal.strArea}"
                        binding.tvContent.text  = meal.strInstructions
                        youtubeLink = meal.strYoutube!!

                    }
                }
                is Resource.Error -> {
                    hideProgressBarForMealDetails()
                    response.message?.let { message ->
//                        Log.e(TAG, "An error occured: $message " )
                        Toast.makeText(activity,"$message", Toast.LENGTH_LONG).show()

                    }
                }
                is Resource.Loading-> {
                    showProgressBarForMealDetails()
                }
            }


        })
    }

    private fun setInformationInViews() {
        Glide.with(binding.root)
            .load(mealThumb)
            .into(binding.imgMealDetail)
        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInformationFromArgs(){
        if(args!=null){
            mealId = args.idMeal
            mealName = args.strMeal
            mealThumb = args.strMealThumb
        }


    }

//    private fun loadingCase(){
//        binding.progressBar.visibility = View.VISIBLE
//        binding.btnSave.visibility = View.INVISIBLE
//        binding.tvInstructions.visibility = View.INVISIBLE
//        binding.tvCategoryInfo.visibility = View.INVISIBLE
//        binding.tvAreaInfo.visibility = View.INVISIBLE
//        binding.imgYoutube.visibility = View.INVISIBLE
//
//    }
//
//    private fun onResponseCase(){
//        binding.progressBar.visibility = View.INVISIBLE
//        binding.btnSave.visibility = View.VISIBLE
//        binding.tvInstructions.visibility = View.VISIBLE
//        binding.tvCategoryInfo.visibility = View.VISIBLE
//        binding.tvAreaInfo.visibility = View.VISIBLE
//        binding.imgYoutube.visibility = View.VISIBLE
//    }
      private fun hideProgressBarForMealDetails() {
         binding.progressBarForMealDetails.visibility = View.INVISIBLE
        binding.btnSave.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tvCategoryInfo.visibility = View.VISIBLE
        binding.tvAreaInfo.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
        }

    private fun showProgressBarForMealDetails() {

        binding.progressBarForMealDetails.visibility = View.VISIBLE
        binding.btnSave.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.tvCategoryInfo.visibility = View.INVISIBLE
        binding.tvAreaInfo.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE

    }


}
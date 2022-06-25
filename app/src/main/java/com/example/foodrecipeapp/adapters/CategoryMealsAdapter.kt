package com.example.foodrecipeapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodrecipeapp.databinding.MealItemBinding
import com.example.foodrecipeapp.pojo.MealByCateogory

class CategoryMealsAdapter : RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewHolder>() {

    private var mealsList:List<MealByCateogory> = ArrayList()
    private lateinit var onItemClick: OnItemCategoryClicked
    private lateinit var onLongCategoryClick:OnLongCategoryClick

    fun setMealList(mealsList: List<MealByCateogory>){
        this.mealsList = mealsList
        notifyDataSetChanged()
    }

    fun setOnLongCategoryClick(onLongCategoryClick:OnLongCategoryClick){
        this.onLongCategoryClick = onLongCategoryClick
    }



    fun onItemClicked(onItemClick: OnItemCategoryClicked){
        this.onItemClick = onItemClick
    }

   inner class CategoryMealsViewHolder(val binding:MealItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewHolder {
        return CategoryMealsViewHolder(MealItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CategoryMealsViewHolder, position: Int) {
        holder.binding.apply {
            tvMealName.text = mealsList[position].strMeal

            Glide.with(holder.itemView)
                    .load(mealsList[position].strMealThumb)
                    .into(imgMeal)
        }

//        holder.itemView.setOnClickListener {
//            onItemClick.onClickListener(mealsList[position])
//        }


    }

    override fun getItemCount(): Int {
        return mealsList.size
    }

    interface OnItemCategoryClicked{
        fun onClickListener(category:MealByCateogory)
    }

    interface OnLongCategoryClick{
        fun onCategoryLongCLick(category:MealByCateogory)
    }
}
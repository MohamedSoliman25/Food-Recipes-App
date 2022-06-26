package com.example.foodrecipeapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodrecipeapp.R
import com.example.foodrecipeapp.databinding.MealItemBinding
import com.example.foodrecipeapp.pojo.Meal

class FavoriteMealsAdapter : RecyclerView.Adapter<FavoriteMealsAdapter.FavoriteViewHolder>() {
//    private var favoriteMeals: List<Meal> = ArrayList()
    private lateinit var onFavoriteClickListener: OnFavoriteClickListener
    private lateinit var onFavoriteLongClickListener: OnFavoriteLongClickListener

    private val diffUtil = object:DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }

    }
        val differ = AsyncListDiffer(this,diffUtil)





    fun setOnFavoriteMealClickListener(onFavoriteClickListener: OnFavoriteClickListener) {
        this.onFavoriteClickListener = onFavoriteClickListener
    }

    fun setOnFavoriteLongClickListener(onFavoriteLongClickListener: OnFavoriteLongClickListener) {
        this.onFavoriteLongClickListener = onFavoriteLongClickListener
    }

   inner class FavoriteViewHolder(val binding: MealItemBinding) :
            RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(MealItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val meal  = differ.currentList[position]
        holder.binding.apply {
            tvMealName.text = meal.strMeal
            Glide.with(holder.itemView)
                    .load(meal.strMealThumb)
                    .error(R.drawable.mealtest)
                    .into(imgMeal)
        }
//
//        holder.itemView.setOnClickListener {
//            onFavoriteClickListener.onFavoriteClick(favoriteMeals[position])
//        }
//
//        holder.itemView.setOnLongClickListener(object : View.OnLongClickListener {
//            override fun onLongClick(p0: View?): Boolean {
//                onFavoriteLongClickListener.onFavoriteLongCLick(favoriteMeals[i])
//                return true
//            }
//        })
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    interface OnFavoriteClickListener {
        fun onFavoriteClick(meal: Meal)
    }

    interface OnFavoriteLongClickListener {
        fun onFavoriteLongCLick(meal: Meal)
    }
}
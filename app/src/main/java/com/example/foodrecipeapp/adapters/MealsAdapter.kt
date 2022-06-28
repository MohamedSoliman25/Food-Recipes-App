package com.example.foodrecipeapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodrecipeapp.R
import com.example.foodrecipeapp.databinding.MealItemBinding
import com.example.foodrecipeapp.pojo.Meal

class MealsAdapter : RecyclerView.Adapter<MealsAdapter.FavoriteViewHolder>() {
//    private var favoriteMeals: List<Meal> = ArrayList()
    private lateinit var onMealClickListener: OnMealClickListener
    private lateinit var onMealLongClickListener: OnMealLongClickListener

    private val diffUtil = object:DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }

    }
        val differ = AsyncListDiffer(this,diffUtil)





    fun setOnMealClickListener(onMealClickListener: OnMealClickListener) {
        this.onMealClickListener = onMealClickListener
    }

    fun setOnLongClickListener(onMealLongClickListener: OnMealLongClickListener) {
        this.onMealLongClickListener = onMealLongClickListener
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
        holder.itemView.setOnClickListener {
            onMealClickListener.onMealClick(meal)
        }
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

    interface OnMealClickListener {
        fun onMealClick(meal: Meal)
    }

    interface OnMealLongClickListener {
        fun onMealLongCLick(meal: Meal)
    }
}
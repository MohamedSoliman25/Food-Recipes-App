package com.example.foodrecipeapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodrecipeapp.databinding.PopularItemsBinding
import com.example.foodrecipeapp.pojo.MealByCateogory

class MostPopularRecyclerAdapter : RecyclerView.Adapter<MostPopularRecyclerAdapter.MostPopularMealViewHolder>(){
    private var mealsList: List<MealByCateogory> = ArrayList()
//    private lateinit var onItemClick: OnItemClick
    lateinit var onItemClick:((MealByCateogory)->Unit)
    private lateinit var onLongItemClick:OnLongItemClick
    fun setMealList(mealsList: List<MealByCateogory>) {
        this.mealsList = mealsList
        notifyDataSetChanged()
    }
//
//    fun setOnClickListener(onItemClick: OnItemClick){
//        this.onItemClick = onItemClick
//    }

    fun setOnLongCLickListener(onLongItemClick:OnLongItemClick){
        this.onLongItemClick = onLongItemClick
    }

    class MostPopularMealViewHolder(val binding: PopularItemsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MostPopularMealViewHolder {
        return MostPopularMealViewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MostPopularMealViewHolder, position: Int) {
         Glide.with(holder.itemView)
                 .load(mealsList[position].strMealThumb)
                 .into(holder.binding.imgPopularMeal)

        holder.itemView.setOnClickListener {
//            onItemClick.onItemClick(mealsList[position])
            onItemClick.invoke(mealsList[position])
        }

        holder.itemView.setOnLongClickListener(object : View.OnLongClickListener{
            override fun onLongClick(p0: View?): Boolean {
                onLongItemClick.onItemLongClick(mealsList[position])
                return true
            }

        })
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }
}

//interface OnItemClick{
//    fun onItemClick(meal:Category)
//}

interface OnLongItemClick{
    fun onItemLongClick(meal:MealByCateogory)
}
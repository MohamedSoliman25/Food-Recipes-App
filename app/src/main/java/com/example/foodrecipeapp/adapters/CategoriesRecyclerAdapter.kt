package com.example.foodrecipeapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodrecipeapp.databinding.CategoryItemBinding
import com.example.foodrecipeapp.pojo.Category
import com.example.foodrecipeapp.pojo.MealByCateogory


class CategoriesRecyclerAdapter : RecyclerView.Adapter<CategoriesRecyclerAdapter.CategoryViewHolder>() {
    private var categoryList:List<Category> = ArrayList()
//    private lateinit var onItemClick: OnItemCategoryClicked
lateinit var onItemClick:((Category)->Unit)

    private lateinit var onLongCategoryClick:OnLongCategoryClick

    fun setCategoryList(categoryList: List<Category>){
        this.categoryList = categoryList
        notifyDataSetChanged()
    }

    fun setOnLongCategoryClick(onLongCategoryClick:OnLongCategoryClick){
        this.onLongCategoryClick = onLongCategoryClick
    }


//
//    fun onItemClicked(onItemClick: OnItemCategoryClicked){
//        this.onItemClick = onItemClick
//    }

   inner class CategoryViewHolder(val binding:CategoryItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.binding.apply {
            tvCategoryName.text = categoryList[position].strCategory

            Glide.with(holder.itemView)
                .load(categoryList[position].strCategoryThumb)
                .into(imgCategory)
        }

        holder.itemView.setOnClickListener {
//            onItemClick.onClickListener(categoryList[position])
            onItemClick.invoke(categoryList[position])

        }


    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
//
//    interface OnItemCategoryClicked{
//        fun onClickListener(category:Category)
//    }

    interface OnLongCategoryClick{
        fun onCategoryLongCLick(category:Category)
    }
}
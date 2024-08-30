package com.gokhanzopcuk.easyfood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gokhanzopcuk.easyfood.databinding.MealItemBinding
import com.gokhanzopcuk.easyfood.pojo.MealList
import com.gokhanzopcuk.easyfood.pojo.MealsByCategory

class CategoryMealsAdapter:RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewModel>() {
    private var mealsList=ArrayList<MealsByCategory>()
    fun setMealsList(mealList:List<MealsByCategory>){
        this.mealsList=mealsList as ArrayList<MealsByCategory>
         notifyDataSetChanged()

    }
    inner class CategoryMealsViewModel (val binding: MealItemBinding):RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewModel {
        val recylerRowBinding=MealItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CategoryMealsViewModel(recylerRowBinding)
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }

    override fun onBindViewHolder(holder: CategoryMealsViewModel, position: Int) {
Glide.with(holder.itemView).load(mealsList[position].strMealThumb).into(holder.binding.imgMeal)
       holder.binding.tvMealName.text=mealsList[position].strMeal

    }

}
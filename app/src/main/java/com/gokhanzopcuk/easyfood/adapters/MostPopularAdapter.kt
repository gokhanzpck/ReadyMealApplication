package com.gokhanzopcuk.easyfood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gokhanzopcuk.easyfood.databinding.PopularItemsBinding
import com.gokhanzopcuk.easyfood.pojo.MealsByCategory

class MostPopularAdapter:RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>() {
         lateinit var onItemClick:((MealsByCategory)->Unit)
       private var mealsList=ArrayList<MealsByCategory>()
     var onLongItemClick:((MealsByCategory)->Unit)?=null

   fun setMeals(mealsList:ArrayList<MealsByCategory>){
       this.mealsList=mealsList
       notifyDataSetChanged()

   }



    class PopularMealViewHolder( var binding:PopularItemsBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        val recylerRowBinding=PopularItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PopularMealViewHolder(recylerRowBinding)

    }

    override fun getItemCount(): Int {
return mealsList.size

    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
       Glide.with(holder.itemView)
           .load(mealsList[position].strMealThumb)
           .into(holder.binding.imgPopularMealItem)
        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealsList[position])

        }
        holder.itemView.setOnLongClickListener{
            onLongItemClick?.invoke(mealsList[position])
            true
        }
    }
}
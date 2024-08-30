package com.gokhanzopcuk.easyfood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gokhanzopcuk.easyfood.adapters.CategoriesAdapter.CategoryViewHolder
import com.gokhanzopcuk.easyfood.databinding.CategoryItemBinding
import com.gokhanzopcuk.easyfood.databinding.PopularItemsBinding
import com.gokhanzopcuk.easyfood.pojo.Category
import com.gokhanzopcuk.easyfood.pojo.CategoryList

class CategoriesAdapter: RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {
  private var categoryList=ArrayList<Category>()
    var onItemClick :((Category)->Unit)? = null

    fun setCategoryList(categoryList:List<Category>){
        this.categoryList=categoryList as ArrayList<Category>
        notifyDataSetChanged()
    }
    inner class CategoryViewHolder( var binding: CategoryItemBinding):RecyclerView.ViewHolder(binding.root){
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
         val rectlerRowBinding=CategoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CategoryViewHolder(rectlerRowBinding)

    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        Glide.with(holder.itemView).load(categoryList[position].strCategoryThumb).into(holder.binding.imgCategory)
        holder.binding.tvCategoryName.text=categoryList[position].strCategory
holder.itemView.setOnClickListener {
    onItemClick!!.invoke(categoryList[position])


}


    }

}
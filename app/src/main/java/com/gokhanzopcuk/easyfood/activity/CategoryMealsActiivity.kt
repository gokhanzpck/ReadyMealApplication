package com.gokhanzopcuk.easyfood.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.gokhanzopcuk.easyfood.R
import com.gokhanzopcuk.easyfood.adapters.CategoryMealsAdapter
import com.gokhanzopcuk.easyfood.databinding.ActivityCategoryMealsActiivityBinding
import com.gokhanzopcuk.easyfood.databinding.ActivityMainBinding
import com.gokhanzopcuk.easyfood.fragments.HomeFragment
import com.gokhanzopcuk.easyfood.viewModel.CategoryMealViewModel

class CategoryMealsActiivity : AppCompatActivity() {
    lateinit var  binding:ActivityCategoryMealsActiivityBinding
     lateinit var categoryMealsViewModel:CategoryMealViewModel
      lateinit var CategoryMealsAdapter:CategoryMealsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsActiivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        prepareRecylerView()
        categoryMealsViewModel=ViewModelProvider(this).get(CategoryMealViewModel::class.java)
        categoryMealsViewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)

        categoryMealsViewModel.observeMealsLiveData().observe(this, Observer { mealsList->
         binding.tvCategoryCount.text=mealsList.size.toString()
      CategoryMealsAdapter.setMealsList(mealsList)

        })
    }
    private fun prepareRecylerView() {
CategoryMealsAdapter= CategoryMealsAdapter()
        binding.rvMeals.apply {
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=CategoryMealsAdapter
        }
    }
}
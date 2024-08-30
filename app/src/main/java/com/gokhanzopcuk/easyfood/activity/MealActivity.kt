package com.gokhanzopcuk.easyfood.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.ViewModelFactoryDsl
import com.bumptech.glide.Glide
import com.gokhanzopcuk.easyfood.R
import com.gokhanzopcuk.easyfood.databinding.ActivityMainBinding
import com.gokhanzopcuk.easyfood.databinding.ActivityMealBinding
import com.gokhanzopcuk.easyfood.db.MealDatabase
import com.gokhanzopcuk.easyfood.fragments.HomeFragment
import com.gokhanzopcuk.easyfood.pojo.Meal
import com.gokhanzopcuk.easyfood.viewModel.MealViewModel
import com.gokhanzopcuk.easyfood.viewModel.MealViewModelFactory

class MealActivity : AppCompatActivity() {
    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var binding: ActivityMealBinding
    private lateinit var mealMvvm:MealViewModel
    private lateinit var yl:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
       // mealMvvm=ViewModelProvider(this).get(MealViewModel::class.java)
         val mealDatabase=MealDatabase.getInstance(this)
        val viewModelFactory=MealViewModelFactory(mealDatabase)
        mealMvvm=ViewModelProvider(this,viewModelFactory)[MealViewModel::class.java]

        getMealinformationFromIntent()
        setInformationInViews()
           loadingCase()
        mealMvvm.getMealDetail(mealId)
        obserMealDetailsLiveData()
        onYoutobleImageClick()
        onFavoriteClick()
    }

    private fun onFavoriteClick() {
        binding.btmAddFav.setOnClickListener {
         mealToSave?.let {
            mealMvvm.insertMeal(it)
             Toast.makeText(this,"Meal Saved",Toast.LENGTH_SHORT).show()

         }

        }
    }


    private fun onYoutobleImageClick() {
        binding.imageYoutuble.setOnClickListener {
            val intent=Intent(Intent.ACTION_VIEW, Uri.parse(yl))
            startActivity(intent)
        }
    }
      private var mealToSave:Meal?=null
    private fun obserMealDetailsLiveData() {
        mealMvvm.observeMealDetailLiveData().observe(this,object :Observer<Meal>{
            override fun onChanged(value: Meal) {
                onResponseCase()
                val meal=value
                mealToSave=meal

              binding.tvCategory.text="Category:${meal!!.strCategory}"
                binding.tvArea.text=meal!!.strArea
                binding.tvInstructionsSteps.text=meal.strInstructions

                yl=meal.strYoutube
            }
        })
    }

    private fun setInformationInViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)
        binding.collapsingToolbar.title=mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))


    }

    private fun getMealinformationFromIntent() {
        val intent=intent
        mealId=intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName=intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb=intent.getStringExtra(HomeFragment.MEAL_THUMB)!!



    }
    private fun loadingCase(){
       // binding.progressBar.visibility=View.VISIBLE
    binding.btmAddFav.visibility=View.INVISIBLE
    binding.tvInstructions.visibility=View.INVISIBLE
        binding.tvCategory.visibility=View.INVISIBLE
        binding.tvArea.visibility=View.INVISIBLE
        binding.imageYoutuble.visibility=View.INVISIBLE

    }
    private fun onResponseCase(){
       // binding.progressBar.visibility=View.INVISIBLE
        binding.btmAddFav.visibility=View.VISIBLE
        binding.tvInstructions.visibility=View.VISIBLE
        binding.tvCategory.visibility=View.VISIBLE
        binding.tvArea.visibility=View.VISIBLE
        binding.imageYoutuble.visibility=View.VISIBLE

    }
}


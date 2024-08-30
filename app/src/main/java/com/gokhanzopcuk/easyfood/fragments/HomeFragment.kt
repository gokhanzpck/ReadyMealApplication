package com.gokhanzopcuk.easyfood.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.gokhanzopcuk.easyfood.R
import com.gokhanzopcuk.easyfood.activity.CategoryMealsActiivity
import com.gokhanzopcuk.easyfood.activity.MainActivity
import com.gokhanzopcuk.easyfood.activity.MealActivity
import com.gokhanzopcuk.easyfood.adapters.CategoriesAdapter
import com.gokhanzopcuk.easyfood.adapters.MostPopularAdapter
import com.gokhanzopcuk.easyfood.databinding.FragmentHomeBinding
import com.gokhanzopcuk.easyfood.fragments.bottomsheet.MealButtomSheetFragment
import com.gokhanzopcuk.easyfood.pojo.Meal
import com.gokhanzopcuk.easyfood.pojo.MealsByCategory
import com.gokhanzopcuk.easyfood.viewModel.HomeViewModel

class HomeFragment : Fragment() {
private lateinit var binding: FragmentHomeBinding
  private lateinit var viewModel:HomeViewModel
  private lateinit var  randomMeal:Meal
 private lateinit var popularItemsAdapte:MostPopularAdapter
 private lateinit var categoriesAdapter:CategoriesAdapter

  companion object{
      const val MEAL_ID="com.gokhanzopcuk.easyfood.fragments.idMeal"
      const val MEAL_NAME="com.gokhanzopcuk.easyfood.fragments.nameMeal"
      const val MEAL_THUMB="com.gokhanzopcuk.easyfood.fragments.thumbMeal"
        const val CATEGORY_NAME=  "com.gokhanzopcuk.easyfood.fragments.categoryName"

  }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
viewModel=(activity as MainActivity ).viewModel
// viewModel=ViewModelProvider(this).get(HomeViewModel::class.java)
popularItemsAdapte= MostPopularAdapter()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        binding= FragmentHomeBinding.inflate(inflater , container, false)
        return  binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         preparePopularItemsRecylerView()
        viewModel.getRandommeal()
        observerRandomMeal()
         onRandomMealClick()
        viewModel.getPopularItems()
        observePopularItemsLiveData()
        popularItemClick()

        prepareCategoryRecylerView()
        viewModel.getCategories()
        observeCategoriesLiveData()

        onCategoryClick()

        onPopulerItemLongClick()
        onSearchIconClick()


    }

    private fun onSearchIconClick() {
binding.imageSearch.setOnClickListener {
    findNavController().navigate(R.id.home_search_geciş)

}

    }

    private fun onPopulerItemLongClick() {
        popularItemsAdapte.onLongItemClick={meal->
            val mealButtomSheetFragment=MealButtomSheetFragment.newInstance(meal.idMeal)
            mealButtomSheetFragment.show(childFragmentManager,"Meal Info ")
        }

    }


    private fun onCategoryClick() {
        categoriesAdapter.onItemClick ={category ->
        val intent=Intent(activity,CategoryMealsActiivity::class.java)
           intent.putExtra(CATEGORY_NAME,category.strCategory)
            startActivity(intent)

        }

    }

    private fun prepareCategoryRecylerView() {
        categoriesAdapter=CategoriesAdapter()
        binding.recViewCategories.apply {
            layoutManager=GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter=categoriesAdapter

        }
    }

    private fun observeCategoriesLiveData() {
        viewModel.observeCategoiesLiveData().observe(viewLifecycleOwner, Observer { categories->
                categoriesAdapter.setCategoryList(categories)

        })
    }

    private fun popularItemClick() {
        popularItemsAdapte.onItemClick={meal->
            val intent=Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularItemsRecylerView() {
        binding.recViewMealsPopular.apply {
            layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
       adapter=popularItemsAdapte
        }
    }
    private fun observePopularItemsLiveData() {
        viewModel.observePopularItemsLiveData().observe(viewLifecycleOwner,
        {mealList->
          popularItemsAdapte.setMeals(mealsList = mealList as ArrayList<MealsByCategory>)
        })
    }
    private fun onRandomMealClick() {
        binding.randomMealCard.setOnClickListener {
            val intent= Intent(activity,MealActivity::class.java)
          intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)

        }
    }
    private fun observerRandomMeal() {
        viewModel.observeRandomMealLiveData().observe(viewLifecycleOwner,
            { meal ->
                Glide.with(this@HomeFragment)
                    .load(meal!!.strMealThumb)
                    .into(binding.imgRandomMeal)
                 this.randomMeal=meal

            })
    }


}
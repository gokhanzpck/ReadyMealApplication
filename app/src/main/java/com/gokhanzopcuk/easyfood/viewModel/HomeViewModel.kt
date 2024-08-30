package com.gokhanzopcuk.easyfood.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gokhanzopcuk.easyfood.db.MealDatabase
import com.gokhanzopcuk.easyfood.pojo.Category
import com.gokhanzopcuk.easyfood.pojo.CategoryList
import com.gokhanzopcuk.easyfood.pojo.MealsByCategoryList
import com.gokhanzopcuk.easyfood.pojo.Meal
import com.gokhanzopcuk.easyfood.pojo.MealList
import com.gokhanzopcuk.easyfood.pojo.MealsByCategory
import com.gokhanzopcuk.easyfood.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query

class HomeViewModel(
    private val mealDatabase: MealDatabase
) :ViewModel(){
    private var favoritesMealsLiveData=mealDatabase.mealDao().getAllMeals()
    private  var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemsLiveData=MutableLiveData<List<MealsByCategory>>()
    private var categoriesLiveData=MutableLiveData<List<Category>>()
     private var buttomSheetMealLiveData=MutableLiveData<Meal>()
         private val searchMealsLiveData=MutableLiveData<List<Meal>>()


   fun getRandommeal(){
       RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
           override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
               if (response.body()!=null){
                   val randomMeal: Meal =response.body()!!.meals[0]
randomMealLiveData.value=randomMeal


               }else{
                   return
               }

           }
           override fun onFailure(call: Call<MealList>, t: Throwable) {
               Log.d("HomeFragment",t.message.toString())
           }


       })
   }

    fun getPopularItems(){
        RetrofitInstance.api.getPopularItems("Seafood").enqueue(object : Callback<MealsByCategoryList>{
            override fun onResponse(call: Call<MealsByCategoryList>, response: Response<MealsByCategoryList>) {
                 if (response.body() != null){
                     popularItemsLiveData.value=response.body()!!.meals

                 }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
Log.d("HomeFragment",t.message.toString())

            }


        })

    }
 fun getCategories(){
     RetrofitInstance.api.getCategories().enqueue(object :Callback<CategoryList>{
         override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
            response.body()?.let {categoryList->
            categoriesLiveData.postValue(categoryList.categories)


            }
         }

         override fun onFailure(call: Call<CategoryList>, t: Throwable) {
Log.e("HomeViewModel",t.message.toString())
         }


     })

 }
    fun getMealById(id:String){
        RetrofitInstance.api.getMealDetails(id).enqueue(object :Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val meal=response.body()?.meals?.first()
                   meal?.let {meal->
                   buttomSheetMealLiveData.postValue(meal)

                   }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e("HomeViewModel",  t.message.toString())
            }


        })

    }




    fun deleteMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().delete(meal)
        }
    }
    fun insertMeal(meal:Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }
    }

    fun searchMeals(searchQuery:String)=RetrofitInstance.api.searchMeals(searchQuery).enqueue(
        object :Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val mealList=response.body()?.meals
                mealList?.let {
                    searchMealsLiveData.postValue(it)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {

               Log.e("HomeViewModel",t.message.toString())
            }

        }
    )

    fun observeSearchMealSliVEData():LiveData<List<Meal>> =searchMealsLiveData



 fun observeCategoiesLiveData():LiveData<List<Category>>{

     return categoriesLiveData
 }



    fun observePopularItemsLiveData():LiveData<List<MealsByCategory>>{
        return popularItemsLiveData

    }


    fun observeRandomMealLiveData():LiveData<Meal>{
        return randomMealLiveData

    }
    fun observeFavoritesMealsLiveData():LiveData<List<Meal>>{
       return favoritesMealsLiveData
    }
    fun observeButtomSheetMeal():LiveData<Meal> = buttomSheetMealLiveData


}
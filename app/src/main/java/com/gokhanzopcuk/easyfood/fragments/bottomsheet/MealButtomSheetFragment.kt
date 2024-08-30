package com.gokhanzopcuk.easyfood.fragments.bottomsheet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.DialogFragmentNavigator
import com.bumptech.glide.Glide
import com.gokhanzopcuk.easyfood.R
import com.gokhanzopcuk.easyfood.activity.MainActivity
import com.gokhanzopcuk.easyfood.activity.MealActivity
import com.gokhanzopcuk.easyfood.databinding.FragmentMealButtomSheetBinding
import com.gokhanzopcuk.easyfood.fragments.HomeFragment
import com.gokhanzopcuk.easyfood.viewModel.HomeViewModel

private const val MEAL_ID = "param1"

class MealButtomSheetFragment : DialogFragment() {
    private var mealId: String? = null
    private lateinit var binding :FragmentMealButtomSheetBinding
   private lateinit var  viewModel:HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEAL_ID)
        }
        viewModel=(activity as MainActivity).viewModel

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {

        binding= FragmentMealButtomSheetBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mealId?.let { viewModel.getMealById(it)
        }
 observeButtomSheetMeal()
        buttomSheetDialogClick()


    }

    private fun buttomSheetDialogClick() {
        binding.buttomSheet.setOnClickListener {
         if (mealName != null && mealThumb!=null){
             val intent=Intent(activity,MealActivity::class.java)
         intent.apply {
             putExtra(HomeFragment.MEAL_ID,mealId)
             putExtra(HomeFragment.MEAL_NAME,mealName)
             putExtra(HomeFragment.MEAL_THUMB,mealThumb)
         }
             startActivity(intent)
         }

        }
    }

    private  var mealName:String?=null
    private  var mealThumb:String?=null


    private fun observeButtomSheetMeal() {
        viewModel.observeButtomSheetMeal().observe(viewLifecycleOwner, Observer {meal->
            Glide.with(this).load(meal.strMealThumb).into(binding.imgButtomSheet)
            binding.tvButtomSheetArea.text=meal.strArea
            binding.tvButtomSheetCt.text=meal.strCategory
            binding.tvButtomSheetMealName.text=meal.strMeal

            mealName=meal.strMeal
            mealThumb=meal.strMealThumb

        })

    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            MealButtomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, param1)
                }
            }
    }
}
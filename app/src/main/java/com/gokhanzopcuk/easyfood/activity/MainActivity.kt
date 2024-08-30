package com.gokhanzopcuk.easyfood.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.gokhanzopcuk.easyfood.R
import com.gokhanzopcuk.easyfood.databinding.ActivityMainBinding
import com.gokhanzopcuk.easyfood.db.MealDatabase
import com.gokhanzopcuk.easyfood.viewModel.HomeViewModel
import com.gokhanzopcuk.easyfood.viewModel.HomeViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var  binding:ActivityMainBinding
    val  viewModel:HomeViewModel by lazy {
        val mealDatabase=MealDatabase.getInstance(this)
        val homeViewModelProviderFactory =HomeViewModelFactory(mealDatabase)
        ViewModelProvider(this,homeViewModelProviderFactory)[HomeViewModel::class.java]

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val buttonNav=binding.btnNav
        val navController=Navigation.findNavController(this, R.id.host_fragment)
        NavigationUI.setupWithNavController(buttonNav,navController)

        











    }
}
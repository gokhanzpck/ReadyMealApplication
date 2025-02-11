package com.gokhanzopcuk.easyfood.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.gokhanzopcuk.easyfood.R
import com.gokhanzopcuk.easyfood.activity.MainActivity
import com.gokhanzopcuk.easyfood.adapters.MealsAdapter
import com.gokhanzopcuk.easyfood.databinding.FragmentSearchBinding
import com.gokhanzopcuk.easyfood.viewModel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {
    private lateinit var binding:FragmentSearchBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var searchRecylerViewAdapter:MealsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel=(activity as MainActivity).viewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        binding= FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    preparaRecylerView()
    binding.imgSearchArrow.setOnClickListener {searchMeals()

        observeSearchMealsLiveData()
        var searchJob:Job?=null
        binding.edSearcBox.addTextChangedListener {searchQuery->
            searchJob?.cancel()
            searchJob=lifecycleScope.launch {
                delay(500)
                viewModel.searchMeals(searchQuery.toString())
            }
        }
    }
    }

    private fun observeSearchMealsLiveData() {
        viewModel.observeSearchMealSliVEData().observe(viewLifecycleOwner, Observer {mealsList->
            searchRecylerViewAdapter.differ.submitList(mealsList)

        })

    }

    private fun searchMeals() {
        val searchQuery =binding.edSearcBox.text.toString()
        if (searchQuery.isNotEmpty()){
            viewModel.searchMeals(searchQuery)
        }

    }

    private fun preparaRecylerView() {
        searchRecylerViewAdapter=MealsAdapter()
        binding.rvSearchView.apply {
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=searchRecylerViewAdapter
        }

    }

}
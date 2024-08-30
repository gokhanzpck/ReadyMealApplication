package com.gokhanzopcuk.easyfood.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

import com.gokhanzopcuk.easyfood.activity.MainActivity
import com.gokhanzopcuk.easyfood.adapters.MealsAdapter
import com.gokhanzopcuk.easyfood.databinding.FragmentFavoritesBinding
import com.gokhanzopcuk.easyfood.viewModel.HomeViewModel
import com.google.android.material.snackbar.Snackbar

class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var  viewModel: HomeViewModel
    private lateinit var favoritesAdapter:MealsAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=(activity as MainActivity).viewModel


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        binding= FragmentFavoritesBinding.inflate(inflater, container, false)
   return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         prapereRecylerView()
         observeFavortes()
        val itemTouchHelper = object :ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT

        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewModel.deleteMeal(favoritesAdapter.differ.currentList[position])
                Snackbar.make(requireView(),"Meal Deleted",Snackbar.LENGTH_LONG).setAction(
                    "Undo ",
                    View.OnClickListener {
                       viewModel.insertMeal(favoritesAdapter.differ.currentList[position])
                    }
                ).show()


            }

        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavorites)


    }

    private fun prapereRecylerView() {
        favoritesAdapter= MealsAdapter()
        binding.rvFavorites.apply {
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=favoritesAdapter

        }
    }

    private fun observeFavortes() {
        viewModel.observeFavoritesMealsLiveData().observe(requireActivity(), Observer { meals->
         favoritesAdapter.differ.submitList(meals)
        })
    }

}
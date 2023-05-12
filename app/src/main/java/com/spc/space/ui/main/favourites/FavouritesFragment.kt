package com.spc.space.ui.main.favourites

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.spc.space.R
import com.spc.space.adapters.FavourtieAdapter
import com.spc.space.databinding.FragmentFavouritesBinding
import com.spc.space.ui.DataStoreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesFragment : Fragment(R.layout.fragment_favourites) {
    private val favouritesViewModel:FavouritesViewModel by viewModels()
    private val dataStoreViewModel:DataStoreViewModel by viewModels()
    private var _binding:FragmentFavouritesBinding? = null
    private val binding get() = _binding!!
    private  lateinit var favouriteAdapter: FavourtieAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavouritesBinding.bind(view)
        favouriteAdapter = FavourtieAdapter()

        val token = dataStoreViewModel.token.value.toString()
        favouritesViewModel.getFavorites("Bearer__$token")

        favouritesViewModel.favourites.observe(viewLifecycleOwner, Observer { data ->
           //
            Log.e("size",data.favoriteItems?.favorites?.size.toString())
            ////////
            favouriteAdapter.differ.submitList(data.favoriteItems.favorites)

        })

binding.favRv.apply {
    adapter = favouriteAdapter
}



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
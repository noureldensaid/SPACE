package com.spc.space.ui.main.favourites

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.spc.space.R
import com.spc.space.adapters.FavouriteAdapter
import com.spc.space.databinding.FragmentFavouritesBinding
import com.spc.space.ui.main.shared_viewmodels.DataStoreViewModel
import com.spc.space.utils.SwipeToDeleteCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesFragment : Fragment(R.layout.fragment_favourites) {
    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!
    private val favouritesViewModel: FavouritesViewModel by viewModels()
    private val dataStoreViewModel: DataStoreViewModel by viewModels()
    private lateinit var favouriteAdapter: FavouriteAdapter
    private lateinit var token: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavouritesBinding.bind(view)
        favouriteAdapter = FavouriteAdapter()


        token = dataStoreViewModel.token.value.toString()
        favouritesViewModel.getFavorites(token)

        binding.favRv.apply { adapter = favouriteAdapter }

        favouritesViewModel.favourites.observe(viewLifecycleOwner, Observer { data ->
            Log.e("size", data.favoriteItems.favorites.size.toString())
            favouriteAdapter.differ.submitList(data.favoriteItems.favorites)
        })

        enableSwipeToDeleteAndUndo()

    }

    private fun enableSwipeToDeleteAndUndo() {
        val swipeToDeleteCallback: SwipeToDeleteCallback =
            object : SwipeToDeleteCallback(activity) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
                    val position = viewHolder.adapterPosition
                    val item = favouriteAdapter.differ.currentList[position]
                    favouritesViewModel.removeFromFavorites(token, item.id)
                    val snackbar = Snackbar
                        .make(
                            view!!,
                            "Item was removed from the list.",
                            Snackbar.LENGTH_LONG
                        )
                    snackbar.setAction("UNDO") {
                        favouritesViewModel.addFavourites(token, item.id)
                        favouritesViewModel.getFavorites(token)
                    }
                    snackbar.setActionTextColor(Color.YELLOW)
                    snackbar.show()
                }
            }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.favRv)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
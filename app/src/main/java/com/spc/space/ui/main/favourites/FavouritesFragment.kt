package com.spc.space.ui.main.favourites

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
import com.spc.space.ui.DataStoreViewModel
import dagger.hilt.android.AndroidEntryPoint

@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class FavouritesFragment : Fragment(R.layout.fragment_favourites) {
    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var favouriteAdapter: FavouriteAdapter
    private val favouritesViewModel: FavouritesViewModel by viewModels()
    private val dataStoreViewModel: DataStoreViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavouritesBinding.bind(view)
        favouriteAdapter = FavouriteAdapter()


        val token = dataStoreViewModel.token.value.toString()
        favouritesViewModel.getFavorites(token)

        binding.favRv.apply { adapter = favouriteAdapter }

        favouritesViewModel.favourites.observe(viewLifecycleOwner, Observer { data ->
            Log.e("size", data.favoriteItems.favorites.size.toString())
            favouriteAdapter.differ.submitList(data.favoriteItems.favorites)
        })

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }


            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val workSpaceItem = favouriteAdapter.differ.currentList[position]
                favouritesViewModel.removeFromFavorites(token, workSpaceItem.id)
                Snackbar.make(view!!, "Workspace removed", Snackbar.LENGTH_LONG)
                    .apply {
                        setAction("UNDO") {
                            favouritesViewModel.addFavourites(token, workSpaceItem.id)
                        }
                    }.show()
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.favRv)

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
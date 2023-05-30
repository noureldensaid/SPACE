package com.spc.space.ui.main.explore.chooseWorkspace

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.spc.space.R
import com.spc.space.adapters.ExploreAdapter
import com.spc.space.databinding.FragmentExploreBinding
import com.spc.space.ui.main.home.LocationViewModel
import com.spc.space.utils.Helper.collectLatestLifecycleFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreFragment : Fragment(R.layout.fragment_explore) {
    private lateinit var exploreAdapter: ExploreAdapter
    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!
    private val exploreFragmentViewModel: ExploreFragmentViewModel by viewModels()
    private val locationViewModel: LocationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentExploreBinding.bind(view)
        locationViewModel.fetchLocation()

        exploreFragmentViewModel.filterByDistance(
//            userLat = locationViewModel.location.value?.latitude ?: 31.241266,
//            userLong = locationViewModel.location.value?.longitude ?: 29.956677
            userLat = 31.23968883739219,
            userLong = 29.96026481128687
        )

        exploreAdapter = ExploreAdapter()

        binding.exploreFragmentRv.apply { adapter = exploreAdapter }

        collectLatestLifecycleFlow(exploreFragmentViewModel.searchResults) { list ->
            exploreAdapter.differ.submitList(list)
        }

        binding.popularChip.apply {
            setOnClickListener {
                collectLatestLifecycleFlow(exploreFragmentViewModel.popularWorkSpaces) { list ->
                    exploreAdapter.differ.submitList(list)
                }
            }
            setOnCloseIconClickListener {
                collectLatestLifecycleFlow(exploreFragmentViewModel.workSpace) { list ->
                    exploreAdapter.differ.submitList(list)
                }
            }
        }

        binding.allChip.apply {
            setOnClickListener {
                collectLatestLifecycleFlow(exploreFragmentViewModel.workSpace) { list ->
                    exploreAdapter.differ.submitList(list)
                }
            }
        }

        binding.nearbyChip.apply {
            setOnClickListener {
                collectLatestLifecycleFlow(exploreFragmentViewModel.nearbyList) { list ->
                    exploreAdapter.differ.submitList(list)
                }
            }
        }

        locationViewModel.place.observe(viewLifecycleOwner, Observer {
            //  binding.userLocation.text = it
            binding.userLocation.text = "Gleem, Alexandria"
        })

        exploreAdapter.onItemClickListener = {
            val data = Bundle()
            data.putParcelable("data", it)
            findNavController().navigate(R.id.action_exploreFragment_to_book_flow, data)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                exploreFragmentViewModel.searchQuery.value = newText.orEmpty().trim()
                return true
            }
        })


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
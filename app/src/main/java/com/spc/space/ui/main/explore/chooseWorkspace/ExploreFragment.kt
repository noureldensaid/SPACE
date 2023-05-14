package com.spc.space.ui.main.explore.chooseWorkspace

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.spc.space.R
import com.spc.space.adapters.ExploreAdapter
import com.spc.space.databinding.FragmentExploreBinding
import com.spc.space.ui.DataStoreViewModel
import com.spc.space.ui.main.home.LocationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreFragment : Fragment(R.layout.fragment_explore) {
    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!
    private val exploreFragmentViewModel: ExploreFragmentViewModel by viewModels()
    private val dataStoreViewModel: DataStoreViewModel by viewModels()
    private val locationViewModel: LocationViewModel by viewModels()
    private lateinit var exploreAdapter: ExploreAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentExploreBinding.bind(view)
        locationViewModel.fetchLocation()

        val token = dataStoreViewModel.token.value.toString()

        exploreAdapter = ExploreAdapter()

        exploreFragmentViewModel.getWorkspaces(token)

        binding.exploreFragmentRv.apply { adapter = exploreAdapter }
        exploreFragmentViewModel.workSpace.observe(viewLifecycleOwner, Observer { data ->
            Log.e("size ", data.workSpace?.size.toString());
            exploreAdapter.differ.submitList(data.workSpace)
        })

        locationViewModel.place.observe(viewLifecycleOwner, Observer {
            binding.userLocation.text = it
        })


        exploreAdapter.onItemClickListener = {
            val data = Bundle()
            data.putParcelable("data", it)
            findNavController().navigate(R.id.action_exploreFragment_to_book_flow, data)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
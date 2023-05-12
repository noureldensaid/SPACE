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
    private val exploreFragmentViewModel: ExploreFragmentViewModel by viewModels()
    private val dataStoreViewModel: DataStoreViewModel by viewModels()
    private val locationViewModel by viewModels<LocationViewModel>()
    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!
    private lateinit var exploreAdapter: ExploreAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentExploreBinding.bind(view)

        exploreAdapter = ExploreAdapter()

        //todo PUT WORKSPACE NAME AND PHOTO IN GRID RV ,
        // then when user click on grid it shows workspace details
        val token = dataStoreViewModel.token.value.toString()

        exploreFragmentViewModel.getWorkspaces(token)

        exploreFragmentViewModel.workSpace.observe(viewLifecycleOwner, Observer { data ->
            //todo send workspace list to the adapter
            // change adapter used class in diff util
            // submit list<workspace> to adpater


            Log.e("size ", data.workSpace?.size.toString());
            exploreAdapter.differ.submitList(data.workSpace)

        })




        locationViewModel.fetchLocation()
        locationViewModel.place.observe(viewLifecycleOwner, Observer {
            binding.userLocation.text = it
        })


        binding.exploreFragmentRv.apply {
            adapter = exploreAdapter
        }


        exploreAdapter.onItemClickListener = {
//            val intent = Intent(requireContext(), WorkSpaceDetailsActivity::class.java)
//            intent.putExtra("key", it)
//            startActivity(intent)
            val data = Bundle()
            data.putParcelable("data", it)
            findNavController().navigate(R.id.action_exploreFragment_to_book_flow, data)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
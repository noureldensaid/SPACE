package com.spc.space.ui.main.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.spc.space.R
import com.spc.space.adapters.FindNearbyAdapter
import com.spc.space.adapters.HomeAdapter
import com.spc.space.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val viewModel by viewModels<HomeFragmentViewModel>()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        val homeAdapter = HomeAdapter()

        binding.apply {
            homeHotFeaturedRv.apply {
                adapter = homeAdapter
            }
        }


        // viewModel.getData()
        viewModel.data.observe(viewLifecycleOwner, Observer { data ->
            Log.e("size ", data.size.toString());
            homeAdapter.differ.submitList(data)
        })

        homeAdapter.onItemClickListener = {
//            val intent = Intent(requireContext(), WorkSpaceDetailsActivity::class.java)
//            intent.putExtra("key", it)
//            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
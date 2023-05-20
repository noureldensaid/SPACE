package com.spc.space.ui.main.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.spc.space.R
import com.spc.space.adapters.HomeAdapter
import com.spc.space.databinding.FragmentHomeBinding
import com.spc.space.ui.main.profile.ProfileViewModel
import com.spc.space.ui.main.shared_viewmodels.DataStoreViewModel
import com.spc.space.utils.Helper.collectLatestLifecycleFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val homeFragmentViewModel: HomeFragmentViewModel by viewModels()
    private val dataStoreViewModel: DataStoreViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()
    lateinit var homeAdapter: HomeAdapter
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        homeAdapter = HomeAdapter()

        binding.apply {
            homeHotFeaturedRv.apply {
                layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                adapter = homeAdapter
            }
        }

        homeAdapter.onItemClickListener = {
            val data = Bundle()
            data.putParcelable("data", it)
            findNavController().navigate(R.id.action_homeFragment_to_book_flow, data)
        }

        collectLatestLifecycleFlow(homeFragmentViewModel.workSpace) { list ->
            homeAdapter.differ.submitList(list?.sortedByDescending { item ->
                item.avgRate
                Log.e("size", "onViewCreated: ${list.size}")
            })
        }

        profileViewModel.user.observe(viewLifecycleOwner, Observer {
            binding.userName.text = "Hello, ${it?.user?.userName.toString().trim().capitalize()}"
            Log.e("UserName", "onViewCreated: ${it}")
        })

        setUpImageSlider()
    }


    private fun setUpImageSlider() {
        val imageUrls: List<SlideModel> = listOf(
            SlideModel(R.drawable.slider_img_1),
            SlideModel(R.drawable.slider_img_3),
            SlideModel(R.drawable.slider_img_4),
            SlideModel(R.drawable.slider_img_2)
        )
        binding.imageSlider.setImageList(imageUrls, ScaleTypes.CENTER_CROP)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
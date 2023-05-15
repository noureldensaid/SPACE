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
import com.spc.space.ui.DataStoreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val homeFragmentViewModel: HomeFragmentViewModel by viewModels()
    private val dataStoreViewModel: DataStoreViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        val homeAdapter = HomeAdapter()


        homeFragmentViewModel.workSpace.observe(viewLifecycleOwner, Observer { data ->
            Log.e("size ", data.workSpace?.size.toString());
            homeAdapter.differ.submitList(data.workSpace)
        })


        dataStoreViewModel.userName.observe(viewLifecycleOwner, Observer {
            binding.userName.text = "Hello, ${it?.capitalize()}"
            Log.e("UserName", "onViewCreated: ${it}")
        })


        binding.apply {
            homeHotFeaturedRv.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                adapter = homeAdapter
            }
        }

        homeAdapter.onItemClickListener = {
            val data = Bundle()
            data.putParcelable("data", it)
            findNavController().navigate(R.id.action_homeFragment_to_book_flow, data)
        }
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
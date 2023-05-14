package com.spc.space.ui.main.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
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
    private lateinit var homeAdapter: HomeAdapter
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        homeAdapter = HomeAdapter()

        val token = dataStoreViewModel.token.value.toString()

        binding.apply {
            homeHotFeaturedRv.apply {
                setHasFixedSize(true)
                layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                adapter = homeAdapter
            }
        }

        homeFragmentViewModel.getWorkspaces(token)

        homeFragmentViewModel.workSpace.observe(viewLifecycleOwner, Observer { data ->
            Log.e("size ", data.workSpace?.size.toString());
            homeAdapter.differ.submitList(data.workSpace)
        })

        dataStoreViewModel.userName.observe(viewLifecycleOwner, Observer {
            binding.userName.text = "Hello, ${it?.capitalize()}"
            Log.e("UserName", "onViewCreated: ${it}")
        })

        homeAdapter.onItemClickListener = {
            val data = Bundle()
            data.putParcelable("data", it)
            findNavController().navigate(R.id.action_homeFragment_to_book_flow, data)

        }
        setUpImageSlider()
    }


    private fun setUpImageSlider() {
        val imageUrls: List<SlideModel> = listOf(
            SlideModel("https://images.milledcdn.com/2017-12-30/5IAWuSdug0e-VqCF/dGWUixFN37A-.jpg"),
            SlideModel("https://images.unsplash.com/photo-1519389950473-47ba0277781c?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80"),
            SlideModel("https://images.unsplash.com/photo-1587275599725-96b7cfa577eb?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1332&q=80"),
            SlideModel("https://plus.unsplash.com/premium_photo-1668383778587-3439a506fdc2?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80")
        )
        binding.imageSlider.setImageList(imageUrls, ScaleTypes.CENTER_CROP)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
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
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        // PUT WORKSPACE NAME AND PHOTO IN GRID RV
        val token = dataStoreViewModel.token.value.toString()
        homeFragmentViewModel.getWorkspaces("Bearer__$token")


        dataStoreViewModel.userName.observe(viewLifecycleOwner, Observer {
            binding.userName.text = "Hello, ${it?.capitalize()}"
            Log.e("UserName", "onViewCreated: ${it}")
        })

        val homeAdapter = HomeAdapter()

        binding.apply {
            homeHotFeaturedRv.apply {
                setHasFixedSize(true)
                layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                adapter = homeAdapter
            }
        }


        // viewModel.getData()
        homeFragmentViewModel.data.observe(viewLifecycleOwner, Observer { data ->
            Log.e("size ", data.size.toString());
            homeAdapter.differ.submitList(data)
        })

        homeAdapter.onItemClickListener = {
//            val intent = Intent(requireContext(), WorkSpaceDetailsActivity::class.java)
//            intent.putExtra("key", it)
//            startActivity(intent)

            val data = Bundle()
            data.putParcelable("data", it)
            findNavController().navigate(R.id.action_homeFragment_to_book_flow, data)

        }

        setUpImageSlider()
    }


    private fun setUpImageSlider() {
        val imageUrls: List<SlideModel> = listOf(
            SlideModel("https://images.unsplash.com/photo-1587275599725-96b7cfa577eb?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1332&q=80"),
            SlideModel("https://plus.unsplash.com/premium_photo-1668383778587-3439a506fdc2?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80"),
            SlideModel("https://images.unsplash.com/photo-1519389950473-47ba0277781c?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80")
        )
        binding.imageSlider.setImageList(imageUrls, ScaleTypes.CENTER_CROP)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
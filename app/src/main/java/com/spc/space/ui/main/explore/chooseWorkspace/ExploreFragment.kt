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
import com.spc.space.ui.main.home.HomeFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreFragment : Fragment(R.layout.fragment_explore) {
    private val viewModel by viewModels<HomeFragmentViewModel>()
    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!
    private lateinit var exploreAdapter: ExploreAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentExploreBinding.bind(view)

        exploreAdapter = ExploreAdapter()

        binding.exploreFragmentRv.apply {
            adapter = exploreAdapter
        }

        viewModel.data.observe(viewLifecycleOwner, Observer {
            Log.e("size ", it.size.toString());
            exploreAdapter.differ.submitList(it)
        })

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
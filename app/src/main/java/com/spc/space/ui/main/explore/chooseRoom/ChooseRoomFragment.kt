package com.spc.space.ui.main.explore.chooseRoom

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.spc.space.R
import com.spc.space.adapters.RoomsAdapter
import com.spc.space.databinding.FragmentChooseRoomBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseRoomFragment : Fragment(R.layout.fragment_choose_room) {
    private val viewModel by viewModels<ChooseRoomViewModel>()
    private var _binding: FragmentChooseRoomBinding? = null
    private val binding get() = _binding!!
    private lateinit var roomsAdapter: RoomsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentChooseRoomBinding.bind(view)
        roomsAdapter = RoomsAdapter()
        binding.roomsRv.apply {
            adapter = roomsAdapter
        }
        viewModel.data.observe(viewLifecycleOwner, Observer {
            Log.e("size ", it.size.toString());
            roomsAdapter.differ.submitList(it)
        })
        roomsAdapter.onItemClickListener = {
            val roomData = Bundle()
            roomData.putParcelable("roomData", it)
            findNavController().navigate(
                R.id.action_chooseRoomFragment_to_roomDetailsFragment,
                roomData
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
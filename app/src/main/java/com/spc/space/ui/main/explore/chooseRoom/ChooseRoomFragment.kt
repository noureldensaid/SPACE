package com.spc.space.ui.main.explore.chooseRoom

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.spc.space.R
import com.spc.space.adapters.RoomsAdapter
import com.spc.space.databinding.FragmentChooseRoomBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseRoomFragment : Fragment(R.layout.fragment_choose_room) {
    private lateinit var roomsAdapter: RoomsAdapter
    private val chooseRoomViewModel: ChooseRoomViewModel by viewModels()
    private var _binding: FragmentChooseRoomBinding? = null
    private val binding get() = _binding!!
    private val args: ChooseRoomFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentChooseRoomBinding.bind(view)
        // workspace data
        val workSpaceItem = args.workspace
        chooseRoomViewModel.getRooms(workSpaceItem!!.id!!)
        binding.apply {
            if (workSpaceItem != null) {
                workspaceNameTitle.text = workSpaceItem.name
            }
        }



        roomsAdapter = RoomsAdapter()
        binding.roomsRv.apply {
            adapter = roomsAdapter
         }
        chooseRoomViewModel.rooms.observe(viewLifecycleOwner, Observer {
            Log.e("size ", it.room?.size.toString());
            roomsAdapter.differ.submitList(it.room)
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
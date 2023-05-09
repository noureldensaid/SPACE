package com.spc.space.ui.main.explore.roomDetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.spc.space.R
import com.spc.space.databinding.FragmentRoomDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoomDetailsFragment : Fragment(R.layout.fragment_room_details) {
    private var _binding: FragmentRoomDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: RoomDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRoomDetailsBinding.bind(view)

        val roomData = args.roomData
        binding.apply {
            Glide.with(view)
                .load(roomData.images?.get(0))
                .transition(DrawableTransitionOptions.withCrossFade())
                .transform(CenterCrop(), RoundedCorners(24))
                .error(R.drawable.error_placeholder)
                .placeholder(R.drawable.placeholder)
                .into(workspaceIv)
        }

        binding.btnSelectDate.setOnClickListener {
            findNavController().navigate(R.id.action_roomDetailsFragment_to_roomBookingFragment)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
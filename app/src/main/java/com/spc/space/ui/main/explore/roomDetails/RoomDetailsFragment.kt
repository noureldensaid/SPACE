package com.spc.space.ui.main.explore.roomDetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.spc.space.R
import com.spc.space.databinding.FragmentRoomDetailsBinding
import com.spc.space.models.workspace.WorkSpaceItem
import com.spc.space.models.workspaceRoom.RoomItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoomDetailsFragment : Fragment(R.layout.fragment_room_details) {
    private var _binding: FragmentRoomDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRoomDetailsBinding.bind(view)

        val roomData = arguments?.getParcelable<RoomItem>("roomData")
        val wsData = arguments?.getParcelable<WorkSpaceItem>("wsData")

        binding.apply {
            if (roomData != null) {
                Glide.with(view)
                    .load(roomData.roomImages?.firstOrNull())
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .transform(CenterCrop(), RoundedCorners(24))
                    .error(R.drawable.error_placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(workspaceIv)

                roomOpenTime.text =
                    "${wsData?.schedule?.openingTime} to ${wsData?.schedule?.closingTime}"
                roomName.text = roomData.roomName?.lowercase()?.capitalize()
                roomCapacity.text = "${roomData.capacity.toString()} guests"
                roomType.text = roomData.type?.capitalize()
                roomPricePerHour.text = "From ${roomData.price}/Hour"


            }

        }

        binding.btnSelectDate.setOnClickListener {
            val args = Bundle()
            args.putParcelable("roomData", roomData)
            findNavController().navigate(
                R.id.action_roomDetailsFragment_to_roomBookingFragment,
                args
            )
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
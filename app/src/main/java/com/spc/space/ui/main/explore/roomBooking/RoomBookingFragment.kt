package com.spc.space.ui.main.explore.roomBooking

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.spc.space.R
import com.spc.space.databinding.FragmentRoomBookingBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RoomBookingFragment : Fragment(R.layout.fragment_room_booking) {
    private var _binding: FragmentRoomBookingBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRoomBookingBinding.bind(view)
        setupCalender()
    }

    @SuppressLint("UseCompatLoadingForDrawables", "NewApi")
    private fun setupCalender() {
        val forwardIcon = resources.getDrawable(R.drawable.ic_calendar_arrow_forward)
        val previousIcon = resources.getDrawable(R.drawable.ic_calendar_arrow_previous)
        binding.calendarView.apply {
            setForwardButtonImage(forwardIcon)
            setPreviousButtonImage(previousIcon)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
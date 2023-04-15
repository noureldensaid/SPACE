package com.spc.space.ui.main.bookings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.spc.space.R
import com.spc.space.databinding.FragmentScucessBookingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuccessBookingFragment : Fragment(R.layout.fragment_scucess_booking) {
    private var _binding: FragmentScucessBookingBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentScucessBookingBinding.bind(view)

        binding.btnGoBookings.setOnClickListener {
            findNavController().navigate(R.id.action_successBookingFragment_to_main_nav_graph)
         }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
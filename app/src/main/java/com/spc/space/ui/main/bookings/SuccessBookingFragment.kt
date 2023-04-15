package com.spc.space.ui.main.bookings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.spc.space.R
import com.spc.space.databinding.FragmentSuccessBookingBinding
import com.spc.space.databinding.FragmentSuccessNewPassBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuccessBookingFragment : Fragment(R.layout.fragment_success_booking) {
    private var _binding: FragmentSuccessBookingBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSuccessBookingBinding.bind(view)


    }

}
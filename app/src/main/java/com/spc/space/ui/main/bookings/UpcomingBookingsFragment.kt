package com.spc.space.ui.main.bookings

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.spc.space.R
import com.spc.space.adapters.UpcomingBookingsAdapter
import com.spc.space.databinding.FragmentUpcomingBookingsBinding
import com.spc.space.ui.DataStoreViewModel
import dagger.hilt.android.AndroidEntryPoint

@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class UpcomingBookingsFragment : Fragment(R.layout.fragment_upcoming_bookings) {
    private val bookingViewModel: BookingsViewModel by viewModels()
    private val dataStoreViewModel: DataStoreViewModel by viewModels()
    private var _binding: FragmentUpcomingBookingsBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUpcomingBookingsBinding.bind(view)

        val token = dataStoreViewModel.token.value.toString()
        val upcomingBookingsAdapter = UpcomingBookingsAdapter()

        bookingViewModel.getBookingsHistory(token)

        binding.upcomingRv.apply {
            adapter = upcomingBookingsAdapter
        }

        bookingViewModel.bookingsHistory.observe(viewLifecycleOwner, Observer { data ->
            Log.e("size ", data.history?.size.toString());
            upcomingBookingsAdapter.differ.submitList(data.history.reversed())
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
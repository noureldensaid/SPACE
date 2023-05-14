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
import com.spc.space.adapters.BookingsHistoryAdapter
import com.spc.space.databinding.FragmentBookingsHistoryBinding
import com.spc.space.ui.DataStoreViewModel
import dagger.hilt.android.AndroidEntryPoint

@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class BookingsHistoryFragment : Fragment(R.layout.fragment_bookings_history) {
    private val bookingViewModel: BookingsViewModel by viewModels()
    private val dataStoreViewModel: DataStoreViewModel by viewModels()
    private var _binding: FragmentBookingsHistoryBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentBookingsHistoryBinding.bind(view)

        val token = dataStoreViewModel.token.value.toString()
        val bookingsHistoryAdapter = BookingsHistoryAdapter()

        bookingViewModel.getBookingsHistory(token)

        binding.historyRv.apply {
            adapter = bookingsHistoryAdapter
        }

        bookingViewModel.bookingsHistory.observe(viewLifecycleOwner, Observer { data ->
            Log.e("size ", data.history?.size.toString());
            bookingsHistoryAdapter.differ.submitList(data.history.reversed())
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
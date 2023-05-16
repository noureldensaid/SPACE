package com.spc.space.ui.main.bookings

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.spc.space.R
import com.spc.space.adapters.BookingsHistoryAdapter
import com.spc.space.databinding.FragmentBookingsHistoryBinding
import com.spc.space.ui.main.shared_viewmodels.DataStoreViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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


        lifecycleScope.launch {
            bookingViewModel.bookingsHistory.collect { state ->
                bookingsHistoryAdapter.differ.submitList(state?.history?.reversed())
            }
        }


        binding.historyRv.apply {
            adapter = bookingsHistoryAdapter
        }

 
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
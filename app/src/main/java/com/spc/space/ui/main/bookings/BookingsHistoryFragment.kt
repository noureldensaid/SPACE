package com.spc.space.ui.main.bookings

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.spc.space.R
import com.spc.space.adapters.BookingsHistoryAdapter
import com.spc.space.adapters.HomeAdapter
import com.spc.space.databinding.FragmentBookingsHistoryBinding
import com.spc.space.databinding.FragmentHomeBinding
import com.spc.space.ui.DataStoreViewModel
import com.spc.space.ui.main.home.HomeFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BookingsHistoryFragment : Fragment(R.layout.fragment_bookings_history) {
    private var _binding: FragmentBookingsHistoryBinding? = null
    private val binding get() = _binding!!
    private val bookingHistoryViewModel: BookingsHistoryViewModel by viewModels()
    private val dataStoreViewModel: DataStoreViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentBookingsHistoryBinding.bind(view)
        val token = dataStoreViewModel.token.value.toString()
        val bookingsHistoryAdapter = BookingsHistoryAdapter()

        bookingHistoryViewModel.getBookingsHistory(token)

        bookingHistoryViewModel.bookingsHistory.observe(viewLifecycleOwner, Observer { data ->

            Log.e("size ", data.history?.size.toString());
            bookingsHistoryAdapter.differ.submitList(data.history)

        })


        binding.historyRv.apply {
            adapter = bookingsHistoryAdapter
        }


    }



}
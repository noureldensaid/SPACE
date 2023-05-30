package com.spc.space.ui.main.bookings

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.spc.space.R
import com.spc.space.adapters.CanceledBookingsAdapter
import com.spc.space.databinding.FragmentCanceledBookingsBinding
import com.spc.space.ui.main.shared_viewmodels.DataStoreViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class CanceledBookingsFragment : Fragment(R.layout.fragment_canceled_bookings) {
    private val bookingViewModel: BookingsViewModel by viewModels()
    private val dataStoreViewModel: DataStoreViewModel by viewModels()
    private var _binding: FragmentCanceledBookingsBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCanceledBookingsBinding.bind(view)

        val token = dataStoreViewModel.token.value.toString()
        val canceledBookingsAdapter = CanceledBookingsAdapter()


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                bookingViewModel.canceledHistory.collect { state ->
                    canceledBookingsAdapter.differ.submitList(state?.history?.reversed())
                }
            }
        }


        binding.canceledRv.apply {
            adapter = canceledBookingsAdapter
        }
//
//        bookingViewModel.canceledHistory.observe(viewLifecycleOwner, Observer { data ->
//            Log.e("size ", data.history?.size.toString());
//            canceledBookingsAdapter.differ.submitList(data.history.reversed())
//        })
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
package com.spc.space.ui.main.bookings

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.spc.space.R
import com.spc.space.adapters.UpcomingBookingsAdapter
import com.spc.space.databinding.FragmentUpcomingBookingsBinding
import com.spc.space.models.upcomingBookings.UpcomingBookings
import com.spc.space.ui.main.shared_viewmodels.DataStoreViewModel
import com.spc.space.utils.Helper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class UpcomingBookingsFragment : Fragment(R.layout.fragment_upcoming_bookings) {
    private val bookingViewModel: BookingsViewModel by viewModels()
    private val dataStoreViewModel: DataStoreViewModel by viewModels()
    private var _binding: FragmentUpcomingBookingsBinding? = null
    private val binding get() = _binding!!
    lateinit var upcomingBookingsAdapter: UpcomingBookingsAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUpcomingBookingsBinding.bind(view)
        val upcomingBookingsAdapter = UpcomingBookingsAdapter()

        binding.upcomingRv.apply {
            adapter = upcomingBookingsAdapter
        }
//
//        lifecycleScope.launch {
//            bookingViewModel.upcomingBookings.collect { state ->
//                upcomingBookingsAdapter.differ.submitList(state?.history?.reversed())
//                Log.e("upcomingBookingsAdapter", state?.history?.size.toString())
//            }
//        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                bookingViewModel.upcomingBookings.collect { state ->
                    upcomingBookingsAdapter.differ.submitList(state.reversed())

                }
            }
        }


        upcomingBookingsAdapter.onItemClickListener = {
            val upcomingBooking = UpcomingBookings(
                bookingId = it.id,
                roomName = it.room.roomName,
                wsName = it.room.workspace.name,
                roomImg = it.room.roomImages.firstOrNull()!!,
                region = it.room.workspace.location.region,
                duration = it.duration,
                price = it.room.price,
                startTime = it.startTime,
                endTime = it.endTime,
                capacity = it.room.capacity
            )
            val args = Bundle().apply {
                putParcelable("booking", upcomingBooking)
            }
            findNavController().navigate(
                R.id.action_bookingsViewPagerFragment_to_bookingDetailsFragment,
                args
            )
            Log.e("room", it.room.roomName.toString())
        }


        val token = dataStoreViewModel.token.value.toString()


//        bookingViewModel.upcomingBookings.observe(viewLifecycleOwner, Observer {
//            upcomingBookingsAdapter.differ.submitList(it.reversed())
//        })


    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
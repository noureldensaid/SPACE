package com.spc.space.ui.main.bookings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.spc.space.R
import com.spc.space.databinding.FragmentBookingDetailsBinding
import com.spc.space.models.upcomingBookings.UpcomingBookings
import com.spc.space.ui.main.shared_viewmodels.DataStoreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingDetailsFragment : Fragment(R.layout.fragment_booking_details) {
    private var _binding: FragmentBookingDetailsBinding? = null
    private val binding get() = _binding!!
    private val bookingsViewModel: BookingsViewModel by viewModels()
    private val dataStoreViewModel: DataStoreViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentBookingDetailsBinding.bind(view)

        val token = dataStoreViewModel.token.value.toString()

        val bookingDetails = arguments?.getParcelable<UpcomingBookings>("booking")

        bookingDetails?.let {
            binding.apply {
                Glide.with(view)
                    .load(bookingDetails.roomImg)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .transform(CenterCrop(), RoundedCorners(24))
                    .error(R.drawable.error_placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(bookingChosenRoomIv)
                chosenRoomName.text = bookingDetails.roomName
                roomLocation.text = bookingDetails.region.toString()
                duration.text = bookingDetails.duration.toString() + " hour(s)"
                bookingPrice.text = bookingDetails.price.toString() + " EGP"
            }
        }

        binding.cancelBookingBtn.setOnClickListener {
            bookingsViewModel.cancelBooking(token, bookingDetails!!.bookingId)
            findNavController().navigate(R.id.action_bookingDetailsFragment_to_homeFragment)

        }


    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
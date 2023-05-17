package com.spc.space.ui.main.bookings

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
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
import com.spc.space.utils.Helper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingDetailsFragment : Fragment(R.layout.fragment_booking_details) {
    private var _binding: FragmentBookingDetailsBinding? = null
    private val binding get() = _binding!!
    private val bookingsViewModel: BookingsViewModel by viewModels()
    private val dataStoreViewModel: DataStoreViewModel by viewModels()
    private lateinit var dialog: Dialog
    lateinit var token: String
    lateinit var bookingId: String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentBookingDetailsBinding.bind(view)

        token = dataStoreViewModel.token.value.toString()

        val bookingDetails = arguments?.getParcelable<UpcomingBookings>("booking")
        bookingId = bookingDetails!!.bookingId

        bookingDetails.let {
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
                bookingTime.text = Helper.convertTimeFormat(
                    bookingDetails.startTime.toString().trim()
                ) + " to " + Helper.convertTimeFormat(bookingDetails.endTime.toString().trim())

            }
        }

        binding.cancelBookingBtn.setOnClickListener {
            bookingsViewModel.cancelBooking(token, bookingDetails.bookingId)
            confirmBooking()

        }


    }

    @SuppressLint("MissingInflatedId")
    private fun confirmBooking() {
        val dialogBinding = layoutInflater.inflate(R.layout.confirm_booking_dialog, null)
        dialog = Dialog(requireContext())
        dialog.setContentView(dialogBinding)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialogBinding.findViewById<TextView>(R.id.message_body_price)
            .setText("Your booking will be canceled")
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val btnYes = dialogBinding.findViewById<Button>(R.id.btnYes).setOnClickListener {
            dialog.dismiss()
            bookingsViewModel.cancelBooking(token, bookingId)
            findNavController().navigate(R.id.action_bookingDetailsFragment_to_homeFragment)
        }
        val btnNo = dialogBinding.findViewById<Button>(R.id.btnNo).setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
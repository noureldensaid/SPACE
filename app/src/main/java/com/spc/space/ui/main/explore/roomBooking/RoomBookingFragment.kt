package com.spc.space.ui.main.explore.roomBooking

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.spc.space.R
import com.spc.space.databinding.FragmentRoomBookingBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class RoomBookingFragment : Fragment(R.layout.fragment_room_booking) {
    private var _binding: FragmentRoomBookingBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRoomBookingBinding.bind(view)
        setupCalender()
        //check in time picker
        setuptimePicker(binding.checkInEt)

        //check out time picker
        setuptimePicker(binding.checkOutEt)
//        binding.checkOutEt.setOnClickListener {
//            openTimePicker { chosenTime ->
//                binding.checkOutEt.placeholderText = chosenTime
//                binding.checkOutEt.hint = ""
//            }
//        }

    }

    private fun setuptimePicker(editText: EditText) {
        editText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                openTimePicker { chosenTime ->
                    editText.hint = chosenTime
                }
            }
        }
        editText.setOnClickListener {
            openTimePicker { chosenTime ->
                editText.hint = chosenTime
            }
        }
    }

    private fun openTimePicker(callback: (String) -> Unit) {
        val isSystem24Hour = is24HourFormat(requireContext())
        val clockFormat =
            if (isSystem24Hour)
                TimeFormat.CLOCK_12H
            else
                TimeFormat.CLOCK_12H
        val timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(clockFormat)
            .setHour(10)
            .setMinute(0)
            .setTitleText("Choose Time")
            .build()
        timePicker.show(childFragmentManager, "Time Picker")
        var chosenTime = ""
        // listener when user choose time(click ok)
        timePicker.addOnPositiveButtonClickListener {
            val hour = timePicker.hour
            val min = timePicker.minute
            // val choosenTime="$hour : $min"
            //  chosenTime = String.format(Locale.getDefault(), "%02d:%02d", hour, min)

            val amPm = if (hour < 12) "AM" else "PM"

            val formattedHour =
                if (hour == 0)
                    "12"
                else if (hour <= 12)
                    hour.toString()
                else
                    (hour - 12).toString()

            chosenTime = String.format(Locale.getDefault(), "%s:%02d %s", formattedHour, min, amPm)
            callback(chosenTime) // Invoke the callback with the chosen time
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables", "NewApi")
    private fun setupCalender() {
        val forwardIcon = resources.getDrawable(R.drawable.ic_calendar_arrow_forward)
        val previousIcon = resources.getDrawable(R.drawable.ic_calendar_arrow_previous)
        binding.calendarView.apply {
            setForwardButtonImage(forwardIcon)
            setPreviousButtonImage(previousIcon)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
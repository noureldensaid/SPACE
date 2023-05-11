package com.spc.space.ui.main.explore.roomBooking

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.spc.space.R
import com.spc.space.databinding.FragmentRoomBookingBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class RoomBookingFragment : Fragment(R.layout.fragment_room_booking) {
    private var _binding: FragmentRoomBookingBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRoomBookingBinding.bind(view)

        setupCalender()
        //check in time picker
        setupTimePicker(binding.checkInEt)
        //check out time picker
        setupTimePicker(binding.checkOutEt)

        val datte = getSelectedDay()



        binding.btnConfirmBookings.setOnClickListener {

            val time1 = binding.checkInEt.text.toString()
            val time2 = binding.checkOutEt.text.toString()
            val date = getSelectedDay()

//            Log.e("time1", time1.toString())
//            Log.e("time2", time2.toString())
//            Log.e("date", datte)

            if (time1.isNotBlank() && time2.isNotBlank()) {
                val checkInTime = parseTime(binding.checkInEt.text.toString())
                val checkOutTime = parseTime(binding.checkOutEt.text.toString())
                val startTime = date + 'T' + checkInTime
                val endTime = date + 'T' + checkOutTime
                val roomId = ""
                val userToken = ""
                val isTimeCorrect = isTimeAfter(time1, time2)
                Log.e("startTime", startTime)
                Log.e("endTime", endTime)

                if (isTimeCorrect) {
                    findNavController().navigate(R.id.action_roomBookingFragment_to_successBookingFragment)
                } else {
                    binding.tvCheckTimeErr.visibility = View.VISIBLE
                    Log.e("startTime", startTime.toString())
                    Log.e("endTime", startTime.toString())

                }

            }

        }

    }

    private fun parseTime(time: String): String {
        val inputFormat = SimpleDateFormat("h:mm a", Locale.US)
        val outputFormat = SimpleDateFormat("HH:mm:ssZ", Locale.US)
        val date = inputFormat.parse(time)
        return outputFormat.format(date!!)
    }


    private fun getSelectedDay(): String {
        val currentDate = LocalDate.now()
        val y = currentDate.year
        val m = currentDate.monthValue
        val d = currentDate.dayOfMonth
        val today = String.format("%04d-%02d-%02d", y, m, d)
        var selectedDate = today
        binding.calendarView.setOnDayClickListener(object : OnDayClickListener {
            override fun onDayClick(eventDay: EventDay) {
                val calendar = eventDay.calendar
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH) + 1
                val day = calendar.get(Calendar.DAY_OF_MONTH)
                selectedDate = String.format("%04d-%02d-%02d", year, month, day)
            }
        })
        return selectedDate
    }


    private fun setupTimePicker(editText: EditText) {
        editText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                openTimePicker { chosenTime ->
                    editText.setText(chosenTime)
                    editText.hint = ""
                }
            }
        }
        editText.setOnClickListener {
            openTimePicker { chosenTime ->
                editText.setText(chosenTime)
                editText.hint = ""
            }
        }
    }

    private fun openTimePicker(callback: (String) -> Unit) {
        val isSystem24Hour = is24HourFormat(requireContext())
        val clockFormat =
            if (isSystem24Hour) TimeFormat.CLOCK_12H else TimeFormat.CLOCK_12H
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
            // val chosenTime="$hour : $min"
            //  chosenTime = String.format(Locale.getDefault(), "%02d:%02d", hour, min)
            val amPm = if (hour < 12) "AM" else "PM"
            val formattedHour =
                if (hour == 0) "12" else if (hour <= 12) hour.toString() else (hour - 12).toString()
            chosenTime = String.format(Locale.getDefault(), "%s:%02d %s", formattedHour, min, amPm)
            // Invoke the callback with the chosen time
            callback(chosenTime)
        }
    }

    // Function to compare two time strings in the format "hh:mm AM/PM"
    private fun isTimeAfter(time1: String, time2: String): Boolean {
        val format = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val parsedTime1 = format.parse(time1)
        val parsedTime2 = format.parse(time2)
        return parsedTime1?.before(parsedTime2) ?: false
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setupCalender() {
        val calendar = binding.calendarView
        val forwardIcon = resources.getDrawable(R.drawable.ic_calendar_arrow_forward)
        val previousIcon = resources.getDrawable(R.drawable.ic_calendar_arrow_previous)
        removePreviousDays()
        calendar.apply {
            setForwardButtonImage(forwardIcon)
            setPreviousButtonImage(previousIcon)
        }

//        {
//            "room":"6457a2897a4e51c466691169",
//            "startTime":"2023-05-07T15:00:00+02:00",
//            "endTime":"2023-05-07T16:30:00+02:00"
//        }


    }


    private fun removePreviousDays() {
        val previousDays = Calendar.getInstance()
        previousDays.add(Calendar.DATE, -1)
        previousDays.set(Calendar.HOUR_OF_DAY, 0)
        previousDays.set(Calendar.MINUTE, 0)
        previousDays.set(Calendar.SECOND, 0)
        previousDays.set(Calendar.MILLISECOND, 0)
        binding.calendarView.setMinimumDate(previousDays)
        binding.calendarView.setDisabledDays(getDisabledDays(previousDays))
    }


    private fun getDisabledDays(calendar: Calendar): List<Calendar> {
        val disabledDays = ArrayList<Calendar>()
        val today = Calendar.getInstance()
        today.set(Calendar.HOUR_OF_DAY, 0)
        today.set(Calendar.MINUTE, 0)
        today.set(Calendar.SECOND, 0)
        today.set(Calendar.MILLISECOND, 0)
        while (calendar.before(today)) {
            disabledDays.add(calendar.clone() as Calendar)
            calendar.add(Calendar.DATE, 1)
        }
        return disabledDays
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
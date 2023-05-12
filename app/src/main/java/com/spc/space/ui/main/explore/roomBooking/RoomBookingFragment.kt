package com.spc.space.ui.main.explore.roomBooking

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.spc.space.R
import com.spc.space.databinding.FragmentRoomBookingBinding
import com.spc.space.models.createBooking.CreateBookingRequest
import com.spc.space.ui.DataStoreViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class RoomBookingFragment : Fragment(R.layout.fragment_room_booking) {
    private val dataStoreViewModel: DataStoreViewModel by viewModels()
    private val roomBookingViewModel: RoomBookingViewModel by viewModels()
    private val args: RoomBookingFragmentArgs by navArgs()
    private var _binding: FragmentRoomBookingBinding? = null
    private val binding get() = _binding!!
    private var date = ""

    //  HTTP 400  -> room is already booked

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRoomBookingBinding.bind(view)


        roomBookingViewModel.booking.observe(viewLifecycleOwner, Observer {
            Log.e("booking", it.message)
        })


//        getSelectedDay { newDate ->
//            date = newDate
//            Log.e("data in vm changed to", "onResume: $date ")
//            Log.e("vm date", "onViewCreated:${roomBookingViewModel.date.value} ")
//        }
        Log.e("vm date", "onViewCreated:${roomBookingViewModel.date.value} ")


        val roomData = args.roomData
        val roomId = roomData.id.toString()
        //   Log.e("roomId", roomId)
        val userToken = dataStoreViewModel.token.value.toString()

        setupCalender()
        //    Log.e("On created : date", defaultDate)
        //check in time picker
        setupTimePicker(binding.checkInEt)
        //check out time picker
        setupTimePicker(binding.checkOutEt)



        binding.btnConfirmBookings.setOnClickListener {
            val time1 = binding.checkInEt.text.toString()
            val time2 = binding.checkOutEt.text.toString()
            var startTime = ""
            var endTime = ""
            var isTimeCorrect = false
            getSelectedDay()


            if (time1.isNotBlank() && time2.isNotBlank()) {
                roomBookingViewModel.date.observe(viewLifecycleOwner, Observer { date ->
                    val checkInTime = parseTime(time1)
                    val checkOutTime = parseTime(time2)
                    startTime = date.toString() + 'T' + checkInTime
                    endTime = date.toString() + 'T' + checkOutTime
                    isTimeCorrect = isTimeAfter(time1, time2)
                })
            } else {
                Toast.makeText(activity, "Please select a time", Toast.LENGTH_LONG).show()
            }



            if (isTimeCorrect) {
                val bookingRequest = CreateBookingRequest(roomId, startTime, endTime)

                roomBookingViewModel.createBooking(userToken, bookingRequest)

                Log.e("start", "onViewCreated: $startTime")
                Log.e("end", "onViewCreated: $endTime")
                Log.e("roomId", "onViewCreated: $roomId")

                getSelectedDay()


                // findNavController().navigate(R.id.action_roomBookingFragment_to_successBookingFragment)
            } else {
                binding.tvCheckTimeErr.visibility = View.VISIBLE
            }


        }
    }


    private fun parseTime(time: String): String {
        val inputFormat = SimpleDateFormat("h:mm a", Locale.US)
        val outputFormat = SimpleDateFormat("HH:mm:ssZ", Locale.US)
        val date = inputFormat.parse(time)
        return outputFormat.format(date!!)
    }


    private fun getSelectedDay() {
        binding.calendarView.setOnDayClickListener(object : OnDayClickListener {
            override fun onDayClick(eventDay: EventDay) {
                val calendar = eventDay.calendar
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH) + 1
                val day = calendar.get(Calendar.DAY_OF_MONTH)
                val selectedDate = String.format("%04d-%02d-%02d", year, month, day)
                roomBookingViewModel.date.value = selectedDate
                Log.e("vm New date", "onViewCreated:${roomBookingViewModel.date.value} ")
            }
        })
    }


//    private fun getSelectedDay(): String {
//        val currentDate = LocalDate.now()
//        val y = currentDate.year
//        val m = currentDate.monthValue
//        val d = currentDate.dayOfMonth
//        val today = String.format("%04d-%02d-%02d", y, m, d)
//        var selectedDate = today
//        binding.calendarView.setOnDayClickListener(object : OnDayClickListener {
//            override fun onDayClick(eventDay: EventDay) {
//                val calendar = eventDay.calendar
//                val year = calendar.get(Calendar.YEAR)
//                val month = calendar.get(Calendar.MONTH) + 1
//                val day = calendar.get(Calendar.DAY_OF_MONTH)
//                selectedDate = String.format("%04d-%02d-%02d", year, month, day)
//                Log.e("selectedDate", "onDayClick:  $selectedDate")
//            }
//        })
//        return selectedDate
//    }


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

        calendar.apply {
            setForwardButtonImage(forwardIcon)
            setPreviousButtonImage(previousIcon)

        }
        disableHoliday()
        removePreviousDays()

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

    private fun disableHoliday() {

        val year = 2023 // replace with your desired year
        val fridayList = mutableListOf<Calendar>()

        // Loop through all 12 months of the year
        for (month in 1..12) {
            // Create a Calendar object for the first day of the month
            val calendar = Calendar.getInstance()
            calendar.set(year, month - 1, 1)

            // Loop through all days of the month
            while (calendar.get(Calendar.MONTH) == month - 1) {
                // Check if the day is a Friday
                if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                    // Add the Friday to the list
                    fridayList.add(calendar.clone() as Calendar)
                }

                // Move to the next day
                calendar.add(Calendar.DAY_OF_MONTH, 1)
            }
        }

        binding.calendarView.setDisabledDays(getDisabledDays(fridayList.toList().firstOrNull()!!))
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
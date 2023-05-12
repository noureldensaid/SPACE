package com.spc.space.ui.main.explore.roomBooking

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.spc.space.R
import com.spc.space.databinding.FragmentRoomBookingBinding
import com.spc.space.models.createBooking.CreateBookingRequest
import com.spc.space.models.workspace.WorkSpaceItem
import com.spc.space.models.workspaceRoom.RoomItem
import com.spc.space.ui.DataStoreViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class RoomBookingFragment : Fragment(R.layout.fragment_room_booking) {
    private val dataStoreViewModel: DataStoreViewModel by viewModels()
    private val roomBookingViewModel: RoomBookingViewModel by viewModels()
    private var _binding: FragmentRoomBookingBinding? = null
    private val binding get() = _binding!!
    private lateinit var dialog: Dialog

    //  HTTP 400  -> room is already booked

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRoomBookingBinding.bind(view)


        roomBookingViewModel.booking.observe(viewLifecycleOwner, Observer {
            Log.e("booking", it.message)
            when (it.message) {
                "Room is currently booked" -> {
                    binding.tvCheckTimeErr.text = "Room is currently busy"
                    binding.tvCheckTimeErr.visibility = View.VISIBLE
                }

            }
        })
        roomBookingViewModel.validBooking.observe(viewLifecycleOwner, Observer {
            if (it == false) {
                binding.tvCheckTimeErr.text = "Invalid booking range"
                binding.tvCheckTimeErr.visibility = View.VISIBLE
            } else {
                binding.tvCheckTimeErr.visibility = View.GONE
            }
        })


//        getSelectedDay { newDate ->
//            date = newDate
//            Log.e("data in vm changed to", "onResume: $date ")
//            Log.e("vm date", "onViewCreated:${roomBookingViewModel.date.value} ")
//        }
        Log.e("vm date", "onViewCreated:${roomBookingViewModel.date.value} ")

        val roomData = arguments?.getParcelable<RoomItem>("roomData")
        val wsData = arguments?.getParcelable<WorkSpaceItem>("wsData")


        val roomId = roomData?.id.toString()
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
                isBookingValid(time1, time2) // time 1 > open time && time 2 < close
                roomBookingViewModel.date.observe(viewLifecycleOwner, Observer { date ->
                    val checkInTime = parseTime(time1)
                    val checkOutTime = parseTime(time2)
                    startTime = date.toString() + 'T' + checkInTime
                    endTime = date.toString() + 'T' + checkOutTime
                    isTimeCorrect = isTimeAfter(time1, time2)
                })
            } else {
                roomBookingViewModel.validBooking.value = false
            }


            // time is correct and checkIn > ws open time
            if (isTimeCorrect) {
                getSelectedDay()
                val bookingRequest =
                    CreateBookingRequest(roomId, addTwoHours(startTime), addTwoHours(endTime))

                if (roomBookingViewModel.validBooking.value == true) {
                    roomBookingViewModel.createBooking(userToken, bookingRequest)
                    Log.e("start", addTwoHours(startTime))
                    Log.e("end", addTwoHours(endTime))
                    Log.e("roomId", "onViewCreated: $roomId")

                    roomBookingViewModel.booking.observe(viewLifecycleOwner, Observer {
                        when (it.message) {
                            "Done" -> {
                                // show dialog and show price if YES save booking details and navigate
                                confirmBooking()
                            }
                        }
                    })


                } else {
                    Log.e("wrong range", "onViewCreated: wrong range")
                }
            } else {
                roomBookingViewModel.validBooking.value = false
            }
        }
    }


    @SuppressLint("MissingInflatedId")
    private fun confirmBooking() {
        val roomData = arguments?.getParcelable<RoomItem>("roomData")

        val dialogBinding = layoutInflater.inflate(R.layout.confirm_booking_dialog, null)
        dialog = Dialog(requireContext())
        dialog.setContentView(dialogBinding)
        dialog.setCancelable(false)
        dialogBinding.findViewById<TextView>(R.id.message_body_price)
            .setText("Total price = ${roomData?.price?.toInt()?.times(5)?.toInt()}")
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val btnYes = dialogBinding.findViewById<Button>(R.id.btnYes).setOnClickListener {
            findNavController().navigate(R.id.action_roomBookingFragment_to_successBookingFragment)

        }
        val btnNo = dialogBinding.findViewById<Button>(R.id.btnNo).setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }


    private fun isBookingValid(time1: String, time2: String): Boolean {
        val wsData = arguments?.getParcelable<WorkSpaceItem>("wsData")

        val timeFormat12 = DateTimeFormatter.ofPattern("h:mm a")
        val timeFormat24 = DateTimeFormatter.ofPattern("HH:mm")

        val startTime = LocalTime.parse(time1, timeFormat12)
        val endTime = LocalTime.parse(time2, timeFormat12)
        val openTime = LocalTime.parse(wsData?.schedule?.openingTime.toString(), timeFormat24)
        val closeTime = LocalTime.parse(wsData?.schedule?.closingTime.toString(), timeFormat24)
        Log.e("openTime", wsData?.schedule?.openingTime.toString())

        // Convert the start and end times to 24-hour clock format
        val startTime24 =
            if (startTime.hour == 12 && startTime.minute == 0 && startTime.format(timeFormat12)
                    .endsWith("AM")
            ) {
                LocalTime.MIDNIGHT
            } else if (startTime.format(timeFormat12).endsWith("PM")) {
                startTime.plusHours(12)
            } else {
                startTime
            }
        val endTime24 =
            if (endTime.hour == 12 && endTime.minute == 0 && endTime.format(timeFormat12)
                    .endsWith("AM")
            ) {
                LocalTime.MIDNIGHT
            } else if (endTime.format(timeFormat12).endsWith("PM")) {
                endTime.plusHours(12)
            } else {
                endTime
            }

        roomBookingViewModel.validBooking.value =
            startTime24.isAfter(openTime) && endTime24.isBefore(closeTime)
        return startTime24.isAfter(openTime) && endTime24.isBefore(closeTime)
    }

    private fun addTwoHours(dateTimeStr: String): String {
        val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")
        val outputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")

        val inputDateTime = OffsetDateTime.parse(dateTimeStr, inputFormat)
        val outputDateTime = inputDateTime.plusHours(2)

        return outputDateTime.format(outputFormat)
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
        removePreviousDays()
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
        dialog = Dialog(requireContext())
        dialog.dismiss()
    }


}
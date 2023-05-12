package com.spc.space.ui.main.explore.roomBooking

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spc.space.data.repository.Repository
import com.spc.space.models.cancelBooking.CancelBookingsResponse
import com.spc.space.models.createBooking.CreateBookingRequest
import com.spc.space.models.createBooking.CreateBookingResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class RoomBookingViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _bookingCanceled: MutableLiveData<CancelBookingsResponse> = MutableLiveData()
    val bookingCanceled: LiveData<CancelBookingsResponse> = _bookingCanceled

    var date: MutableLiveData<String> = MutableLiveData()

    var validBooking: MutableLiveData<Boolean> = MutableLiveData(true)

    private val _booking: MutableLiveData<CreateBookingResponse> = MutableLiveData()
    val booking: LiveData<CreateBookingResponse> = _booking

    init {
        getTodayDate()
    }


    private fun getTodayDate() {
        viewModelScope.launch {
            val currentDate = LocalDate.now()
            val y = currentDate.year
            val m = currentDate.monthValue
            val d = currentDate.dayOfMonth
            date.postValue(String.format("%04d-%02d-%02d", y, m, d))
            Log.e("date", "getTodayDate: ${date.value}")
        }
    }


    fun createBooking(userToken: String, bookingRequest: CreateBookingRequest) =
        viewModelScope.launch {
            try {
                val response = repository.createBooking(userToken, bookingRequest)
                if (response != null) {
                    _booking.postValue(response)
                    Log.e("booking req created", response.message)
                } else Log.e("booking request", response.message)
            } catch (ex: Exception) {
                _booking.postValue(
                    CreateBookingResponse(
                        message = "Room is currently booked",
                        null
                    )
                )
                Log.e("TAG", ex.message.toString());
            }
        }


    fun cancelBooking(userToken: String, bookingId: String) =
        viewModelScope.launch {
            try {
                val response = repository.cancelBooking(userToken, bookingId)
                if (response != null) {
                    _bookingCanceled.postValue(response)
                    Log.e("booking canceled ? ", response.message)
                } else Log.e("booking canceled ? ", response.message)
            } catch (ex: Exception) {
                Log.e("ERROR", ex.message.toString());
            }
        }


}
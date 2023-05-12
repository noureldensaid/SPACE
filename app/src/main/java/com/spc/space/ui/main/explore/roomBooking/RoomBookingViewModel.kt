package com.spc.space.ui.main.explore.roomBooking

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spc.space.data.repository.Repository
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

    var date: MutableLiveData<String> = MutableLiveData()

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
                    Log.e("booking req created", "BookingReq: Great")
                } else Log.e("booking request", "getData: Failed")
            } catch (ex: Exception) {
                Log.e("booking request", "getData: Failed")
                Log.e("TAG", ex.message.toString());
            }
        }

}
package com.spc.space.ui.main.explore.roomBooking

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spc.space.data.repository.Repository
import com.spc.space.models.createBooking.CreateBookingRequest
import com.spc.space.models.createBooking.CreateBookingResponse
import com.spc.space.models.workspace.WorkspacesResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomBookingViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel()  {

    private val _booking: MutableLiveData<CreateBookingResponse> = MutableLiveData()
    val booking: LiveData<CreateBookingResponse> = _booking


    fun createBooking(userToken: String,bookingRequest: CreateBookingRequest ) = viewModelScope.launch {
        try {
            val response = repository.createBooking(userToken,bookingRequest)
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
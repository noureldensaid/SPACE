package com.spc.space.ui.main.bookings

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spc.space.data.repository.DataStoreRepository
import com.spc.space.data.repository.Repository
import com.spc.space.models.bookingsHistory.BookingHistoryResponse
import com.spc.space.models.bookingsHistory.History
import com.spc.space.models.cancelBooking.CancelBookingsResponse
import com.spc.space.models.canceledBookingsHistory.CanceledBookingsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class BookingsViewModel @Inject constructor(
    private val repository: Repository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {


    init {
        viewModelScope.launch {
            val token = dataStoreRepository.getToken().toString()
            getBookingsHistory(token)
            getCanceledBookings(token)
            getUpcomingBookings(token)
        }
    }



    private val _bookingCanceled: MutableLiveData<CancelBookingsResponse> = MutableLiveData()
    val bookingCanceled: LiveData<CancelBookingsResponse> = _bookingCanceled

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


    private val _bookingsHistory: MutableStateFlow<BookingHistoryResponse?> =
        MutableStateFlow(null)
    val bookingsHistory: StateFlow<BookingHistoryResponse?> = _bookingsHistory

    private val _canceledHistory: MutableStateFlow<CanceledBookingsResponse?> =
        MutableStateFlow(null)
    val canceledHistory: StateFlow<CanceledBookingsResponse?> = _canceledHistory

    private val _unfilteredBookings: MutableStateFlow<List<History>?> =
        MutableStateFlow(null)
    val unfilteredBookings: StateFlow<List<History>?> = _unfilteredBookings

    private val _upcomingBookings: MutableStateFlow<List<History>> =
        MutableStateFlow(emptyList())
    val upcomingBookings: StateFlow<List<History>> = _upcomingBookings



    private suspend fun getBookingsHistory(token: String) {
        try {
            val response = repository.getBookingsHistory(token)
            if (response != null) {
                _unfilteredBookings.value = response.history
                Log.e("Boooking History request", "getHistory: Great")
                getFilteredBookingHistory()
            } else Log.e("Boooking History request", "getHistory: Failed")
        } catch (ex: Exception) {
            Log.e("TAG", ex.message.toString())
        }
    }

    private suspend fun getCanceledBookings(token: String) {
        try {
            val response = repository.getCanceledBookingsHistory(token)
            if (response != null) {
                _canceledHistory.value = response
                Log.e("Get canceled Bookings History request", "getHistory: Great")
            } else Log.e("Get canceled Bookings History request", "getHistory: Failed")
        } catch (ex: Exception) {
            Log.e("TAG", ex.message.toString())
        }
    }

    private suspend fun getUpcomingBookings(token: String) {
        try {
            val response = repository.getBookingsHistory(token)
            if (response != null) {
                _bookingsHistory.value = response
                Log.e("getUpcomingBookings request", "getHistory: Great")
                Log.e("getUpcomingBookings request size", response.history.size.toString())
                getFilteredBookingHistory()
            } else Log.e("getUpcomingBookings request", "getHistory: Failed")
        } catch (e: Exception) {
            Log.e("Exception", e.message.toString())
        }
    }

    private fun getFilteredBookingHistory() {
         val currentInstant = Instant.now()
        val currentDateTime = LocalDateTime.ofInstant(currentInstant, ZoneOffset.UTC)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val result = _unfilteredBookings.value?.filter { item ->
          item.isUpcoming!! &&
                    LocalDateTime.parse(item.startTime, formatter)
                        .isAfter(currentDateTime)
        } ?: emptyList()

        viewModelScope.launch {
            _upcomingBookings.emit(result)
        }
    }

}
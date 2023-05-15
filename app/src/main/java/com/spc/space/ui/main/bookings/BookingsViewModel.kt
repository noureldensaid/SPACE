package com.spc.space.ui.main.bookings

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spc.space.data.repository.DataStoreRepository
import com.spc.space.data.repository.Repository
import com.spc.space.models.bookingsHistory.BookingHistoryResponse
import com.spc.space.models.bookingsHistory.History
import com.spc.space.models.canceledBookingsHistory.CanceledBookingsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class BookingsViewModel @Inject constructor(
    private val repository: Repository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            val token = dataStoreRepository.getToken().toString()
            val formatter = DateTimeFormatter.ISO_DATE_TIME
            getBookingsHistory(token)
            getCanceledBookings(token)
            getUpcomingBookings(token)
        }
    }

    // unfiltered
    private val _bookingsHistory: MutableLiveData<BookingHistoryResponse> = MutableLiveData()
    val bookingsHistory: LiveData<BookingHistoryResponse> = _bookingsHistory

    private val _canceledHistory: MutableLiveData<CanceledBookingsResponse> = MutableLiveData()
    val canceledHistory: LiveData<CanceledBookingsResponse> = _canceledHistory

    private val _unfilteredBookings: MutableLiveData<List<History>> = MutableLiveData()
    private val unfilteredBookings: LiveData<List<History>> = _unfilteredBookings


    private val _upcomingBookings: MutableLiveData<List<History>> = MutableLiveData()
    val upcomingBookings: LiveData<List<History>> = _upcomingBookings


    private fun getBookingsHistory(token: String) = viewModelScope.launch {
        try {
            val response = repository.getBookingsHistory(token)
            if (response != null) {
                _unfilteredBookings.postValue(response.history)
                Log.e("Boooking History request", "getHistory: Great")
                getFilteredBookingHistory()
            } else Log.e("Boooking History request", "getHistory: Failed")
        } catch (ex: Exception) {
            Log.e("TAG", ex.message.toString());
        }
    }

    private fun getCanceledBookings(token: String) = viewModelScope.launch {
        try {
            val response = repository.getCanceledBookingsHistory(token)
            if (response != null) {
                _canceledHistory.postValue(response)
                Log.e("Get canceled Bookings History request", "getHistory: Great")
            } else Log.e("Get canceled Bookings History request", "getHistory: Failed")
        } catch (ex: Exception) {
            Log.e("TAG", ex.message.toString());
        }
    }


    private fun getUpcomingBookings(token: String) = viewModelScope.launch {
        try {
            val response = repository.getBookingsHistory(token)
            if (response != null) {
                _bookingsHistory.postValue(response)
                Log.e("getUpcomingBookings request", "getHistory: Great")
                Log.e("getUpcomingBookings request size", response.history.size.toString())
                getFilteredBookingHistory()
            } else Log.e("getUpcomingBookings request", "getHistory: Failed")
        } catch (ex: Exception) {
            Log.e("TAG", ex.message.toString());
        }
    }


    private fun getFilteredBookingHistory() {
        val currentInstant = Instant.now()
        val currentDateTime = LocalDateTime.ofInstant(currentInstant, ZoneOffset.UTC)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val result = _unfilteredBookings.value?.filter { item ->
            !item.isCancelled &&
                    LocalDateTime.parse(item.startTime, formatter)
                        .isAfter(currentDateTime)
        } ?: emptyList()
        _upcomingBookings.postValue(result)
    }
}

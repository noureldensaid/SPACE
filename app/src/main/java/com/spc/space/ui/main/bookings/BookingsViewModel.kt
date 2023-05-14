package com.spc.space.ui.main.bookings

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spc.space.data.repository.Repository
import com.spc.space.models.bookingsHistory.BookingHistoryResponse
import com.spc.space.models.canceledBookingsHistory.CanceledBookingsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BookingsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    //  private val formatter = DateTimeFormatter.ISO_DATE_TIME

    // unfiltered
    private val _bookingsHistory: MutableLiveData<BookingHistoryResponse> = MutableLiveData()
    val bookingsHistory: LiveData<BookingHistoryResponse> = _bookingsHistory

    private val _canceledHistory: MutableLiveData<CanceledBookingsResponse> = MutableLiveData()
    val canceledHistory: LiveData<CanceledBookingsResponse> = _canceledHistory

//    //filtered
//    private val _upcomingBookings = MutableStateFlow(emptyList<UpcomingDto>())
//    val upcomingBookings = _upcomingBookings.asStateFlow()
//
//    private val _unFilteredBookings = MutableStateFlow(listOf<History>())
//    val unFilteredBookings = _unFilteredBookings.asStateFlow()


    fun getBookingsHistory(token: String) = viewModelScope.launch {
        try {
            val response = repository.getBookingsHistory(token)
            if (response != null) {
                _bookingsHistory.postValue(response)
                Log.e("Boooking History request", "getHistory: Great")
            } else Log.e("Boooking History request", "getHistory: Failed")
        } catch (ex: Exception) {
            Log.e("TAG", ex.message.toString());
        }
    }

    fun getCanceledBookings(token: String) = viewModelScope.launch {
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


//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun filterBookings() {
//        val today = LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC)
//        val upcomingBookings = unFilteredBookings.filter { booking ->
//            val eventDate =
//                LocalDateTime.parse(booking.startTime, formatter).toInstant(ZoneOffset.UTC)
//            eventDate.isBefore(today) || eventDate == today && !booking.isCancelled
//        }.map { booking ->
//            val date = LocalDateTime.parse(booking.startTime, formatter).toLocalDate()
//            UpcomingDto(
//                startTime = date.toString(),
//                endTime = booking.endTime!!,
//                duration = booking.duration!!,
//                id = booking.id!!,
//                isCancelled = booking.isCancelled
//            )
//        }
//        _filteredEvents.value = filteredEvents
//    }


//    fun getUpcomingBookings(token: String) = viewModelScope.launch {
//        try {
//            val response = repository.getBookingsHistory(token)
//            if (response != null) {
//                val historyList = response.history
//
//
//
//
//
//
//
//
//                Log.e("Boooking History request", "getHistory: Great")
//            } else Log.e("Boooking History request", "getHistory: Failed")
//        } catch (ex: Exception) {
//            Log.e("TAG", ex.message.toString());
//        }
//    }


}

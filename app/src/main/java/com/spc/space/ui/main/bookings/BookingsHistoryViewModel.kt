package com.spc.space.ui.main.bookings

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spc.space.data.repository.Repository
import com.spc.space.models.bookingsHistory.BookingHistoryResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BookingsHistoryViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _bookingsHistory: MutableLiveData<BookingHistoryResponse> = MutableLiveData()
    val bookingsHistory: LiveData<BookingHistoryResponse> = _bookingsHistory

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

}

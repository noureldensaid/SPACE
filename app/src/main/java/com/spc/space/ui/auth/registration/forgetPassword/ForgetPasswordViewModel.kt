package com.spc.space.ui.auth.registration.forgetPassword

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spc.space.data.repository.AuthRepository
import com.spc.space.models.auth.forgetPassword.ForgetPasswordRequest
import com.spc.space.models.auth.forgetPassword.ForgetPasswordResponse
import com.spc.space.models.auth.signUp.SignUpRequest
import com.spc.space.models.auth.signUp.SignUpResponse
import com.spc.space.models.createBooking.CreateBookingRequest
import com.spc.space.models.createBooking.CreateBookingResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _forgetPassResponse: MutableLiveData<ForgetPasswordResponse> = MutableLiveData()
    val forgetPassResponse: LiveData<ForgetPasswordResponse> = _forgetPassResponse


    fun forgetPassword(token: String, request: ForgetPasswordRequest) {
        viewModelScope.launch {
            try {
                val response= authRepository.forgetPassword(token,request)
                if (response != null) {
                    _forgetPassResponse.postValue(response)
                    Log.e("forget pass req", response.message)
                } else Log.e("forget pass req", response.message)
            } catch (e: Exception) {
                Log.e("error forget pass", "OTP err")
            }
        }
    }






}
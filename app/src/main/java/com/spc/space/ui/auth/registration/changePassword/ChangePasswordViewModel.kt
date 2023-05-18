package com.spc.space.ui.auth.registration.changePassword

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spc.space.data.repository.AuthRepository
import com.spc.space.models.auth.changePassword.ChangePasswordRequest
import com.spc.space.models.auth.changePassword.ChangePasswordRespone

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _changePasswordResponse: MutableLiveData<ChangePasswordRespone> = MutableLiveData()
    val changePasswordResponse: LiveData<ChangePasswordRespone> = _changePasswordResponse

    fun changePassword( request: ChangePasswordRequest) {
        viewModelScope.launch {
            try {
                val response= authRepository.changePassword(request)
                if (response != null) {
                    _changePasswordResponse.postValue(response)
                    Log.e("change pass req",response.message)
                } else Log.e("change pass req", response.message)
            } catch (e: Exception) {
                Log.e("error forget pass", "OTP err")
            }
        }
    }






}
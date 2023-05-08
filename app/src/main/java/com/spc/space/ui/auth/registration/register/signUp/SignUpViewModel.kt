package com.spc.space.ui.auth.registration.register.signUp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spc.space.data.repository.AuthRepository
import com.spc.space.models.auth.signUp.SignUpRequest
import com.spc.space.models.auth.signUp.SignUpResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _signUpResponse: MutableLiveData<SignUpResponse> = MutableLiveData()
    val signUpResponse: LiveData<SignUpResponse> = _signUpResponse

    fun signUp(request: SignUpRequest) {
        viewModelScope.launch {
            try {
                _signUpResponse.value = authRepository.signUp(request)
            } catch (e: Exception) {
                Log.e("error signUp", "signUp: ${e.message}")
            }
        }
    }


}
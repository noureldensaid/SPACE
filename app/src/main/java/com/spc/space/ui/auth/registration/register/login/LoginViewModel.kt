package com.spc.space.ui.auth.registration.register.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spc.space.data.repository.AuthRepository
import com.spc.space.models.auth.signIn.SignInRequest
import com.spc.space.models.auth.signIn.SignInResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    // private val dataStoreRepository: DataStoreRepository,
) : ViewModel() {

//    init {
//        getToken()
//    }
//
//    private val _token: MutableLiveData<String?> = MutableLiveData()
//    val token: LiveData<String?> = _token


    private val _signInResponse: MutableLiveData<SignInResponse> = MutableLiveData()
    val signInResponse: LiveData<SignInResponse> = _signInResponse

    fun signIn(request: SignInRequest) {
        viewModelScope.launch {
            try {
                _signInResponse.value = authRepository.signIn(request)
            } catch (e: Exception) {
                _signInResponse.value = SignInResponse("you have to confirm email first", "401", "")
            }
        }
    }

//    fun saveToken(token: String) {
//        viewModelScope.launch {
//            dataStoreRepository.saveToken("token", token)
//        }
//    }
//
//    private fun getToken() {
//        viewModelScope.launch {
//            try {
//                val tokenResponse = dataStoreRepository.getToken("token")
//                if (tokenResponse != null) _token.value = tokenResponse
//                else _token.value = null
//            } catch (e: Exception) {
//                Log.e(" token error", "validateToken: Invalid token")
//            }
//        }
//    }
//
//    fun clearToken() {
//        viewModelScope.launch {
//            dataStoreRepository.clearToken()
//            _token.value = null
//        }
//    }

}
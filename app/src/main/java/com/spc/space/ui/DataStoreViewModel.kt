package com.spc.space.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spc.space.data.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataStoreViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {


    private val _token: MutableLiveData<String?> = MutableLiveData()
    val token: LiveData<String?> = _token


    private val _userInfo: MutableLiveData<String?> = MutableLiveData()
    val userInfo: LiveData<String?> = _userInfo

    init {
        getToken()
        Log.e("user info", ":${userInfo.value} ")
    }

//    fun saveUserInfo(response: SignUpResponse)  {
//         _userInfo.value = User(
//            email = response.savedUser.email,
//            password = response.savedUser.password,
//            userName = response.savedUser.userName,
//            userToken = _token.value,
//            favorites = response.savedUser.favorites
//        )
////        Log.e("USER INFO", "saveUserInfo: ${_userInfo.userName}")
////        Log.e("USER INFO LIVE DATA", "saveUserInfo: ${_userInfo.value?.userName}")
////        _userInfo.postValue(result)
////        return result
//    }



    fun saveToken(userToken: String) {
        viewModelScope.launch {
            dataStoreRepository.saveToken(userToken)
        }
    }

    private fun getToken() {
        viewModelScope.launch {
            try {
                val tokenResponse = dataStoreRepository.getToken()
                if (tokenResponse != null) _token.value = tokenResponse
                else _token.value = null
            } catch (e: Exception) {
                Log.e(" token error", "validateToken: Invalid token")
            }
        }
    }

    fun clearToken() {
        viewModelScope.launch {
            dataStoreRepository.clearToken()
            Log.e("Clear token", "clearToken:  ${_token.value} ")
            _token.value = null
        }
    }


}
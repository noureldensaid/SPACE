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


    private val _userName: MutableLiveData<String?> = MutableLiveData()
    val userName: LiveData<String?> = _userName



    init {
        getToken()
        getUserName()
    }

    fun saveUserName(userName: String) {
        viewModelScope.launch {
            dataStoreRepository.saveUserName(userName)
        }
    }

    private fun getUserName() {
        viewModelScope.launch {
            val result = dataStoreRepository.getUserName()
            if (result != null) _userName.postValue(result)
            else _userName.postValue("null")
        }
    }


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
package com.spc.space.ui.main.shared_viewmodels

import android.net.Uri
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

    private val _userPic: MutableLiveData<String?> = MutableLiveData()
    val userPic: LiveData<String?> = _userPic


    init {
        getToken()
        getUserName()
        getUri()
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

    fun saveUri(uri: Uri?) {
        viewModelScope.launch {
            dataStoreRepository.saveUri(uri)
        }
    }

    private fun getUri() {
        viewModelScope.launch {
            try {
                val uri = dataStoreRepository.getUri()
                if (uri != null) _userPic.value = uri
                else _userPic.value = null
            } catch (e: Exception) {
                Log.e(" uri error", "validateToken: Invalid uri")
            }
        }
    }

    fun clearUri() {
        viewModelScope.launch {
            dataStoreRepository.clearUri()
            Log.e("Clear uri", "clear:  ${_userPic.value} ")
            _userPic.value = null
        }
    }


}
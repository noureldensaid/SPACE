package com.spc.space.ui.main.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spc.space.data.repository.DataStoreRepository
import com.spc.space.data.repository.Repository
import com.spc.space.models.userdata.UserDataResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val repository: Repository,
    val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _user: MutableLiveData<UserDataResponse> = MutableLiveData()
    val user: LiveData<UserDataResponse> = _user

    init {
        viewModelScope.launch {
            val token = dataStoreRepository.getToken().toString()
            getUserData(token)
        }
    }

    private fun getUserData(userToken: String) =
        viewModelScope.launch {
            try {
                val response =
                    repository.getUserData(userToken)
                if (response != null) {
                    _user.postValue(response)
                    Log.e("getUserData? ", response.message.toString())
                } else Log.e("getUserData ? ", "Failed")
            } catch (ex: Exception) {
                Log.e("TAG", ex.message.toString())
            }
        }


}
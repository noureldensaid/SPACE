package com.spc.space.ui.main.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spc.space.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {

    private val _msg: MutableLiveData<String> = MutableLiveData()
    val msg: LiveData<String> = _msg


    fun updateUserProfilePic(userToken: String, image: RequestBody) =
        viewModelScope.launch {
            try {
                val response =
                    repository.updateProfilePic(userToken, image)
                if (response != null) {
                    _msg.postValue(response.message)
                    Log.e("updateUserProfilePic? ", response.message.toString())
                } else Log.e("updateUserProfilePic ? ", "Failed")
            } catch (ex: Exception) {
                Log.e("TAG", ex.message.toString())
            }
        }


}
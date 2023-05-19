package com.spc.space.ui.main.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spc.space.data.repository.Repository
import com.spc.space.models.updatePassword.UpdatePasswordRequest
import com.spc.space.models.updatePassword.UpdatePasswordResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdatePasswordViewModel@Inject constructor(
    private val repository: Repository
) : ViewModel() {
    var data:MutableLiveData<String> = MutableLiveData()

    private val _updatePassword:MutableLiveData<UpdatePasswordResponse> = MutableLiveData()
    val updatePassword:LiveData<UpdatePasswordResponse> =_updatePassword

    fun updatePassword(userToken:String,updatePasswordRequest: UpdatePasswordRequest)=
        viewModelScope.launch {
            try {
                val response = repository.updatePassword(userToken,updatePasswordRequest)
                if (response !=null){
                    _updatePassword.postValue(response)
                    Log.e("password updated?","done")
                }else Log.e("password updated?","Failed")

            }catch (ex: Exception)
            {
                Log.e("TAG", ex.message.toString());
            }
        }
}
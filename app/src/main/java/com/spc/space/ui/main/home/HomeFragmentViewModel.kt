package com.spc.space.ui.main.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spc.space.data.repository.Repository
import com.spc.space.models.fake.UnsplashPhoto
 import com.spc.space.models.workspace.WorkspacesResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

//    private val _data: MutableLiveData<List<UnsplashPhoto>> = MutableLiveData()
//    val data: LiveData<List<UnsplashPhoto>> = _data


    private val _workSpace: MutableLiveData<WorkspacesResponse> = MutableLiveData()
    val workSpace: LiveData<WorkspacesResponse> = _workSpace

    init {

    }


//    private fun getData() = viewModelScope.launch {
//        try {
//            val response = repository.getData()
//            if (response.isSuccessful) {
//                _data.postValue(response.body()?.results)
//                Log.e("Great request", "getData: Great")
//            } else Log.e("Failed request", "getData: Failed")
//        } catch (ex: Exception) {
//            Log.e("TAG", ex.message.toString());
//        }
//    }

    fun getWorkspaces(token: String) = viewModelScope.launch {
        try {
            val response = repository.getWorkspaces(token)
            if (response != null) {
                _workSpace.postValue(response)
                Log.e("workspace fetched", "getWorkspaces: Great")
            } else Log.e("workspace request", "getData: Failed")
        } catch (ex: Exception) {
            Log.e("TAG", ex.message.toString());
        }
    }
}
package com.spc.space.ui.main.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spc.space.data.repository.DataStoreRepository
import com.spc.space.data.repository.Repository
import com.spc.space.models.fake.UnsplashPhoto
import com.spc.space.models.workspaces.WorkSpaceItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val repository: Repository,
    private val dataStoreRepo: DataStoreRepository
) : ViewModel() {

    private val _data: MutableLiveData<List<WorkSpaceItem>> = MutableLiveData()
    val data: LiveData<List<WorkSpaceItem>> = _data

    init {
        viewModelScope.launch {
            val token = dataStoreRepo.getToken()
            getData("Bearer__$token")
        }
    }


    private fun getData(token: String) = viewModelScope.launch {
        try {
            val response = repository.getData(token)
            if (response.isSuccessful) {
                _data.postValue(response.body()?.workSpace ?: emptyList())
                Log.e("Great request", "getData: Great")
            } else Log.e("Failed request", "getData: Failed")
        } catch (ex: Exception) {
            Log.e("TAG", ex.message.toString());
        }
    }

}
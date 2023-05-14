package com.spc.space.ui.main.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spc.space.data.repository.Repository
import com.spc.space.models.workspace.WorkspacesResponse
import com.spc.space.utils.Constants.MY_TOKEN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    private val _workSpace: MutableLiveData<WorkspacesResponse> = MutableLiveData()
    val workSpace: LiveData<WorkspacesResponse> = _workSpace

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
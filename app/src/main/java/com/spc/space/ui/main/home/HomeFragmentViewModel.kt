package com.spc.space.ui.main.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spc.space.data.repository.DataStoreRepository
import com.spc.space.data.repository.Repository
import com.spc.space.models.workspace.WorkSpaceItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val repository: Repository,
    private val dataStoreRepository: DataStoreRepository,
) : ViewModel() {

    init {
        viewModelScope.launch {
            val token = dataStoreRepository.getToken().toString()
            getWorkspaces(token)
        }
    }


    private val _workSpace: MutableStateFlow<List<WorkSpaceItem>?> = MutableStateFlow(null)
    val workSpace = _workSpace.asStateFlow()

    private fun getWorkspaces(token: String) = viewModelScope.launch {
        try {
            val response = repository.getWorkspaces(token)
            if (response != null) {
                _workSpace.value = response.workSpace
                Log.e("workspace fetched", "getWorkspaces: Great")
            } else Log.e("workspace request", "getData: Failed")
        } catch (ex: Exception) {
            Log.e("TAG123", ex.message.toString());
        }
    }
}
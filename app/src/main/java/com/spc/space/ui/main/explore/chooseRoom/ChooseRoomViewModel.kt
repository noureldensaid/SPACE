package com.spc.space.ui.main.explore.chooseRoom

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spc.space.data.repository.Repository
import com.spc.space.models.workspaceRoom.RoomResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseRoomViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _rooms: MutableLiveData<RoomResponse> = MutableLiveData()
    val rooms: LiveData<RoomResponse> = _rooms


    fun getRooms(workspaceId: String) = viewModelScope.launch {
        try {
            val response = repository.getRoomsForWorkspace(workspaceId)
            if (response != null) {
                _rooms.postValue(response)
                Log.e("Great request", "getData: Great")
            } else Log.e("Failed request", "getData: Failed")
        } catch (ex: Exception) {
            Log.e("TAG", ex.message.toString());
        }
    }

}
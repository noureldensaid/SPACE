package com.spc.space.ui.main.explore.workspaceDetails

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spc.space.data.repository.Repository
import com.spc.space.models.favs.AddFavoritesResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class WorkSpaceDetailsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    var date: MutableLiveData<String> = MutableLiveData()

    //var favourite: MutableLiveData<Boolean> = MutableLiveData(true)
    private  val _favourite :MutableLiveData<AddFavoritesResponse> = MutableLiveData()
    val favourite:LiveData<AddFavoritesResponse> = _favourite

    fun AddFavourites(userToken:String,workspaceId: String) = viewModelScope.launch {
        try {
            val response = repository.addToFavorites(userToken,workspaceId)
            if (response != null){
                _favourite.postValue(response)
                Log.e("addFavourite Request created",response.message)
            }else Log.e("addFavourite Request",response.message)
        }catch (ex: Exception){
            _favourite.postValue(


                AddFavoritesResponse(
                    /////////
                   message ="add failed", null
                )
            )
            Log.e("TAG",ex.message.toString())
        }
    }
    }

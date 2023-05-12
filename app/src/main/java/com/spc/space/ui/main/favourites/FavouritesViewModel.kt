package com.spc.space.ui.main.favourites

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spc.space.data.repository.Repository
import com.spc.space.models.favs.GetFavoritesResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private  val repository: Repository
 ): ViewModel(){

     private val _favourites :MutableLiveData<GetFavoritesResponse> = MutableLiveData()
     val favourites :LiveData<GetFavoritesResponse> =_favourites

    fun getFavorites(token:String) = viewModelScope.launch {
        try {
            val response = repository.getFavorites(token)
            if (response != null){
                _favourites.postValue(response)
                Log.e("Great request", "getData: Great")
            } else Log.e("Failed request", "getData: Failed")
        } catch (ex: Exception) {
            Log.e("TAG", ex.message.toString());
            }
        }
    }

package com.spc.space.ui.main.favourites

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spc.space.data.repository.Repository
import com.spc.space.models.favs.AddFavoritesResponse
import com.spc.space.models.favs.DeleteFavoritesResponse
import com.spc.space.models.favs.GetFavoritesResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _favourites: MutableLiveData<GetFavoritesResponse> = MutableLiveData()
    val favourites: LiveData<GetFavoritesResponse> = _favourites

    private val _newFavouritesList: MutableLiveData<AddFavoritesResponse> = MutableLiveData()
    val newFavouritesList: LiveData<AddFavoritesResponse> = _newFavouritesList

    private val _deletedItem: MutableLiveData<DeleteFavoritesResponse> = MutableLiveData()
    val deletedItem: LiveData<DeleteFavoritesResponse> = _deletedItem


    fun getFavorites(token: String) = viewModelScope.launch {
        try {
            val response = repository.getFavorites(token)
            if (response != null) {
                _favourites.postValue(response)
                Log.e("fav fetched ? ", "done")
            } else Log.e("fav fetched", " Failed")
        } catch (ex: Exception) {
            Log.e("TAG", ex.message.toString());
        }
    }

    fun addFavourites(userToken: String, workspaceId: String) =
        viewModelScope.launch {
            try {
                val response = repository.addToFavorites(userToken, workspaceId)
                if (response != null) {
                    _newFavouritesList.postValue(response)
                    Log.e("addFavourite Request created", response.message)
                } else Log.e("addFavourite Request", response.message)
            } catch (ex: Exception) {
                _newFavouritesList.postValue(
                    AddFavoritesResponse(message = "add failed", null)
                )
                Log.e("TAG", ex.message.toString())
            }
        }

    fun removeFromFavorites(userToken: String, workspaceId: String) =
        viewModelScope.launch {
            try {
                val response = repository.removeFromFavorites(userToken, workspaceId)
                if (response != null) {
                    _deletedItem.postValue(response)
                    Log.e("deleteFav Request created", response.message)
                } else Log.e("deleteFav Request", response.message)
            } catch (ex: Exception) {
                _deletedItem.postValue(
                    DeleteFavoritesResponse(message = "add failed", null)
                )
                Log.e("TAG", ex.message.toString())
            }
        }


}

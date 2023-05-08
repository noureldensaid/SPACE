package com.spc.space.ui.main.explore.chooseWorkspace

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spc.space.data.repository.Repository
import com.spc.space.models.fake.UnsplashPhoto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreFragmentViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _data: MutableLiveData<List<UnsplashPhoto>> = MutableLiveData()
    val data: LiveData<List<UnsplashPhoto>> = _data

    init {
        getData()
    }


    private fun getData() = viewModelScope.launch {
        try {
            val response = repository.getData()
            if (response.isSuccessful) {
                _data.postValue(response.body()?.results)
                Log.e("Great request", "getData: Great")
            } else Log.e("Failed request", "getData: Failed")
        } catch (ex: Exception) {
            Log.e("TAG", ex.message.toString());
        }
    }

}
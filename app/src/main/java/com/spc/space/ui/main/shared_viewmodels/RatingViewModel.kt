package com.spc.space.ui.main.shared_viewmodels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spc.space.data.repository.Repository
import com.spc.space.models.createReviewRequest.CreateReviewRequest
import com.spc.space.models.createReviewRequest.CreateReviewResponse
import com.spc.space.models.getReview.GetReviewResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class RatingViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel(){

    var data: MutableLiveData<String> = MutableLiveData()
    private val _rating:MutableLiveData<CreateReviewResponse> = MutableLiveData()
    val rating:LiveData<CreateReviewResponse> = _rating

     private  val _rattingForWs: MutableLiveData<GetReviewResponse> = MutableLiveData()
     val rattingForWs :LiveData<GetReviewResponse> =_rattingForWs



    fun createReview(userToken:String,workspaceId: String,reviewRequest: CreateReviewRequest)=
        viewModelScope.launch {
            try {
               val response = repository.createReview(userToken,workspaceId,reviewRequest)
                if (response != null){
                    _rating.postValue(response)
                    Log.e("review created",response.message)

                }else Log.e("review request",response.message)

            }catch (ex: Exception){

                Log.e("TAG", ex.message.toString());
            }
        }

    fun getReview(userToken: String,workspaceId: String)=viewModelScope.launch {
        try {
            val response = repository.getReview(userToken, workspaceId)
            if (response != null) {
                _rattingForWs.postValue(response)
                Log.e("rating fetched ?", "done ${response.avgRating}")
            } else
                Log.e("rating fetched ?", "failed")

        } catch (ex: Exception) {
            Log.e("TAG", ex.message.toString());
        }
    }
}
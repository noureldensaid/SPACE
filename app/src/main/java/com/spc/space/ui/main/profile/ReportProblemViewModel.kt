package com.spc.space.ui.main.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spc.space.data.repository.Repository
import com.spc.space.models.reportProblem.ReportProblemRequest
import com.spc.space.models.reportProblem.ReportProblemResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportProblemViewModel  @Inject constructor(
    private val repository: Repository
) : ViewModel(){

    var data :MutableLiveData<String> = MutableLiveData()

     private val _reportProblem:MutableLiveData<ReportProblemResponse> = MutableLiveData()
     val rProblem:LiveData<ReportProblemResponse> = _reportProblem

    fun createReportProblem(userToken:String,workspaceId: String,problemRequest: ReportProblemRequest)=
        viewModelScope.launch {
            try {
                val response = repository.createReportProblem(userToken,workspaceId, problemRequest )
                if (response != null){
                    _reportProblem.postValue(response)
                    Log.e("report problem created?","done")

                }else Log.e("report problem created? ","Failed")
            }catch (ex: Exception)
            {
                Log.e("TAG", ex.message.toString());
            }
        }
}
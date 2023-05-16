package com.spc.space.ui.main.explore.chooseWorkspace

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spc.space.data.repository.DataStoreRepository
import com.spc.space.data.repository.Repository
import com.spc.space.models.workspace.WorkSpaceItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sqrt

@HiltViewModel
class ExploreFragmentViewModel @Inject constructor(
    private val repository: Repository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            val token = dataStoreRepository.getToken().toString()
            getWorkspaces(token)
        }
    }

//    private val _nearby: MutableStateFlow<List<WorkSpaceItem>?> = MutableStateFlow(null)
//    val nearby = _nearby.asStateFlow()


    private val _workSpace = MutableStateFlow<List<WorkSpaceItem>?>(null)
    val workSpace = _workSpace.asStateFlow().filterNotNull()

    val searchQuery = MutableStateFlow("")

    val popularWorkSpaces = _workSpace.asStateFlow().filterNotNull().map { list ->
        list.filter { item ->
            item.avgRate!! > 3
        }
    }

    private fun getWorkspaces(token: String) = viewModelScope.launch {
        try {
            val response = repository.getWorkspaces(token)
            if (response != null) {
                _workSpace.value = response.workSpace
                Log.e("Great request", "getData: Great")
            } else Log.e("Failed request", "getData: Failed")
        } catch (ex: Exception) {
            Log.e("TAG", ex.message.toString())
        }
    }

    val searchResults: StateFlow<List<WorkSpaceItem>> = combine(
        _workSpace,
        searchQuery
    ) { workSpaces, query ->
        if (query.isEmpty()) {
            workSpaces
                ?: emptyList() // if the query is empty, return the original list or an empty list if it's null
        } else {
            workSpaces?.filter { item ->
                item.name!!.contains(
                    query,
                    ignoreCase = true
                ) || item.location.region!!.contains(
                    query,
                    ignoreCase = true
                ) || item.avgRate.toString().contains(
                    query,
                    ignoreCase = true
                ) || item.location.city!!.contains(
                    query,
                    ignoreCase = true
                ) || item.location.streetName.toString().contains(
                    query,
                    ignoreCase = true
                )
            }
                ?: emptyList() // filter the list based on the query or return an empty list if it's null
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        emptyList()
    )
    // initialize the filtered list to an empty list

    private fun distanceTo(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val earthRadius = 6371.0 // Earth radius in kilometers
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a =
            kotlin.math.sin(dLat / 2) * kotlin.math.sin(dLat / 2) + cos(Math.toRadians(lat1)) * cos(
                Math.toRadians(lat2)
            ) * kotlin.math.sin(dLon / 2) * kotlin.math.sin(dLon / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return earthRadius * c // Distance in kilometers
    }


    private val _nearbyList = MutableStateFlow<List<WorkSpaceItem>?>(null)
    val nearbyList = _nearbyList.asStateFlow().filterNotNull()


    fun filterByDistance(
        userLat: Double,
        userLong: Double,
        distance: Double = 1.00,
        list: List<WorkSpaceItem>? = _workSpace.value,
    ) {
        if (list != null) {
            _nearbyList.value = list.filter { workspace ->
                val workspaceLat = workspace.location.latitude!!.toDouble()
                val workspaceLon = workspace.location.longitude!!.toDouble()
                val workspaceDistance =
                    distanceTo(
                        workspaceLat,
                        workspaceLon,
                        userLat,
                        userLong,
                    )
                workspaceDistance <= distance
            }.sortedByDescending { workSpaceItem ->
                workSpaceItem.distance
            }
        }
    }


//    private fun filterByDistance(lat: Double, lon: Double) {
//        _workSpace.value?.let { workSpaces ->
//            val filteredList = workSpaces.forEach { item ->
//                val distance = distanceTo(
//                    lat, lon, item.location.latitude!!.toDouble(),
//                    item.location.longitude!!.toDouble()
//                )
//                if (distance > 5) {
//                    _distance.value = emptyList()
//                } else {
//                    item.distance = distance // Update the distance of the item
//                    true // Include items that are within the allowed distance
//                }
//            }
//        }
//    }


}
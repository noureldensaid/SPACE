package com.spc.space.ui.main.home

import android.annotation.SuppressLint
import android.app.Application
import android.location.Geocoder
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {



    // don't worry
     @SuppressLint("StaticFieldLeak")
     private val context = getApplication<Application>().applicationContext

    private var fusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private var _location = MutableLiveData<Location?>()
    val location: LiveData<Location?> = _location

    private var _place = MutableLiveData<String>()
    val place: LiveData<String> = _place


    @SuppressLint("MissingPermission")
    fun fetchLocation() {
        fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
            val location: Location? = task.result
            if (location == null) {
                provideNewLocation()
            } else {
                _location.postValue(location)
                getCityName(location)
                Log.e("null location", "fetchLocation: ")
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun provideNewLocation() {
        val locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest, locationCallback, Looper.myLooper()
        )
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var lastLocation: Location = locationResult.lastLocation!!
            Log.d("Debug:", "your last last location: " + lastLocation.longitude.toString())
        }
    }


    private fun getCityName(location: Location) {
        val lat = location.latitude
        val lng = location.longitude
        var address = ""
        var countryName = ""
        val geoCoder = Geocoder(context, Locale.getDefault())
        if (lat != null && lng != null) {
            val addressList = geoCoder.getFromLocation(lat, lng, 1)
            address = addressList?.get(0)?.locality ?: ""
            _place.postValue(address)
            countryName = addressList?.get(0)?.countryName ?: ""
            Log.d("Debug:", "Your City: " + _place + " ; your Country " + countryName)
        }
    }

}

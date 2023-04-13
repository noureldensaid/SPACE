package com.spc.space.ui.main.home

import android.annotation.SuppressLint
import android.app.Application
import android.location.Geocoder
import android.location.Location
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.LocationServices
import java.util.*


class LocationViewModel(application: Application) : AndroidViewModel(application) {
    // don't worry
    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    private var fusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private var _location = MutableLiveData<Location>()
    val location: LiveData<Location> = _location

    private var _place = MutableLiveData<String>()
    val place: LiveData<String> = _place


    @SuppressLint("MissingPermission")
    fun fetchLocation() {
        fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
            val location: Location = task.result
            if (location != null) {
                _location.postValue(location)
                getCityName()
                // provideNewLocation()
            } else {
                Log.e("null location", "fetchLocation: ", )
            }
        }
    }






    private fun getCityName() {
        val lat = _location.value?.latitude
        val lng = _location.value?.longitude
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

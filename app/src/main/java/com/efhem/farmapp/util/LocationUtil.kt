package com.efhem.farmapp.util

import android.annotation.SuppressLint
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnSuccessListener


/**
 * They are two ways of getting location i.e Network provider(fusedLocationClient) and location manager
 *
 * fusedLocationClient accuracy better than locationManager
 * but fusedLocationClient solely depend on if an app (e.g google map) on the device as
 * lastknownlocation else our app should request for location update
 * */
object LocationUtil {

    const val REQUEST_LOCATION_PERMISSION = 100
    const val REQUEST_CHECK_LOCATION_SETTINGS = 200

    private val LOCATION_PERMISSIONS = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var mFragment: Fragment? = null


    fun initLocationRequest(fragment: Fragment): LocationUtil {
        mFragment = fragment
        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(fragment.requireContext())
        return this
    }

    @SuppressLint("MissingPermission")
    fun requestLocation(callback: (Location?) -> Unit) {
        if (fusedLocationClient == null || mFragment == null) {
            throw Exception("you need to call initLocationRequest")
        }
        // Check Permission to access User location: ACCESS_FINE_LOCATION
        if (!DeviceRequestUtil.checkPermissions(mFragment!!.requireContext(), LOCATION_PERMISSIONS)) {
            mFragment?.requestPermissions(LOCATION_PERMISSIONS, REQUEST_LOCATION_PERMISSION)
            return
        }

        fusedLocationClient?.lastLocation?.addOnSuccessListener(mFragment!!.requireActivity(),
            OnSuccessListener(callback))
    }

    /**
     * fusedLocationClient fast response solely depend on if an app (e.g google map) on the device
     * as last known location else our app should request for location update.
     */
    @SuppressLint("MissingPermission")
    fun requestLocationUpdates(callback: (LocationResult?) -> Unit): LocationCallback {
        if (fusedLocationClient == null || mFragment == null) {
            throw Exception("you need to call initLocationRequest")
        }

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)
                locationResult?.let { callback.invoke(it) }
            }
        }

        // Check Permission to access User location: ACCESS_FINE_LOCATION
        if (!DeviceRequestUtil.checkPermissions(mFragment!!.requireContext(), LOCATION_PERMISSIONS)) {
            mFragment?.requestPermissions(LOCATION_PERMISSIONS, REQUEST_LOCATION_PERMISSION)
            return locationCallback
        }

        val locationRequest = LocationRequest.create()
        //we will set our timer to 3 seconds
        //but it is expected to consider the time which the farmer will move to the next coordinate
        locationRequest.interval = 6000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        fusedLocationClient?.requestLocationUpdates(locationRequest, locationCallback, null)

        // This can be used by caller to removeLocationUpdate().
        return locationCallback
    }

    fun removeLocationUpdates(locationCallback: LocationCallback) {
        mFragment = null
        fusedLocationClient?.removeLocationUpdates(locationCallback)
    }

    fun checkLocationRequestSettings(fragment: Fragment, callback: (response: LocationSettingsResponse) -> Unit) {
        // Dummy request to test availability of location services.
        val locationRequest = LocationRequest().apply {
            interval = 1200000  // in millisecs
            fastestInterval = 60000 // in millisecs
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        // Before you start requesting for location updates,
        // you need to check the state of the user’s location settings.
        val task = LocationServices.getSettingsClient(fragment.requireActivity())
            .checkLocationSettings(builder.build())

        task.addOnSuccessListener(fragment.requireActivity(), callback)

        task.addOnFailureListener { e ->
            if (e is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    e.startResolutionForResult(
                        fragment.requireActivity(), REQUEST_CHECK_LOCATION_SETTINGS
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                    Log.d("Location settings error", sendEx.stackTrace.toString())
                }
            }
            //else //todo: consider adding a dialog or toast
        }
    }

    fun isPermissionGranted(grantResults: IntArray): Boolean {
        return grantResults.isNotEmpty()
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
            && grantResults[1] == PackageManager.PERMISSION_GRANTED
    }

}

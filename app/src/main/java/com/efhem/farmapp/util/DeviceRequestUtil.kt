package com.efhem.farmapp.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

object DeviceRequestUtil {


    const val PERMISSION_ALL = 1

    private val PERMISSIONS = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    fun takeIfAllPermissionGranted(fragment: Fragment, action: () -> Unit) {
        if (checkPermissions(fragment.requireContext(), PERMISSIONS).not()) {
            fragment.requestPermissions(PERMISSIONS, PERMISSION_ALL)
        }else action()
    }

//    fun checkPermissions(context: Context): Boolean {
//        var permitted = true
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            PERMISSIONS.forEach { permission ->
//                permitted = permitted && ContextCompat.checkSelfPermission(context, permission) ==
//                        PackageManager.PERMISSION_GRANTED
//            }
//        }
//        return permitted
//    }

    fun checkPermissions(context: Context, permissions: Array<String>): Boolean {
        var permitted = true
        permissions.forEach { permission ->
            permitted = permitted &&
                    ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        }
        return permitted
    }
}
package com.efhem.farmapp.util

import com.google.android.gms.maps.model.LatLng

import com.google.android.gms.maps.model.LatLngBounds

object MapUtil {

    fun getPolygonLatLngBounds(polygon: List<LatLng>): LatLngBounds? {
        val centerBuilder = LatLngBounds.builder()
        for (point in polygon) {
            centerBuilder.include(point)
        }
        return centerBuilder.build()
    }
}
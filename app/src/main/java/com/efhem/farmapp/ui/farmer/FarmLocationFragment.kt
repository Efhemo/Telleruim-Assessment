package com.efhem.farmapp.ui.farmer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.efhem.farmapp.R
import com.efhem.farmapp.databinding.FragmentFarmLocationBinding
import com.efhem.farmapp.ui.FarmCoordinateActivity
import com.efhem.farmapp.util.K
import com.efhem.farmapp.util.MapUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions


class FarmLocationFragment : Fragment(R.layout.fragment_farm_location), OnMapReadyCallback {

    private var _bind: FragmentFarmLocationBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val bind get() = _bind!!
    private var mMap: GoogleMap? = null


    private val captureLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val capturedLocation = result.data?.getStringExtra(K.CAPTURE_LOCATION_ENTRY)

            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _bind = FragmentFarmLocationBinding.bind(view)
//        bind.takeLocation.setOnClickListener {
//            captureLauncher.launch(Intent(requireContext(), FarmCoordinateActivity::class.java))
//        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }


    companion object {
        @JvmStatic
        fun newInstance() = FarmLocationFragment()
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap
        val sydney = LatLng(-34.0, 151.0)
        mMap?.addMarker(
            MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney")
        )
        //mMap?.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        // Add polygons to indicate areas on the map.
        val polygon1 = googleMap?.addPolygon(
            PolygonOptions().clickable(true)
                .add(
                    LatLng(-27.457, 153.040),
                    LatLng(-33.852, 151.211),
                    LatLng(-37.813, 144.962),
                    LatLng(-34.928, 138.599)
                )
        )
        polygon1?.tag = "alpha"

        polygon1?.points?.let {
            val latLngBounds = MapUtil.getPolygonLatLngBounds(it)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 200))
        }

    }
}
package com.efhem.farmapp.ui.farmer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.efhem.farmapp.R
import com.efhem.farmapp.databinding.FragmentFarmLocationBinding
import com.efhem.farmapp.domain.repositories.Coordinate
import com.efhem.farmapp.ui.FarmCoordinateActivity
import com.efhem.farmapp.ui.FarmViewModel
import com.efhem.farmapp.util.K
import com.efhem.farmapp.util.LocationUtil
import com.efhem.farmapp.util.MapUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import kotlinx.android.synthetic.main.fragment_farm_location.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class FarmLocationFragment : Fragment(R.layout.fragment_farm_location), OnMapReadyCallback {

    private var _bind: FragmentFarmLocationBinding? = null
    private val bind get() = _bind!!

    private var mMap: GoogleMap? = null
    private val viewModel by sharedViewModel<FarmViewModel>()
    var locationUtil: LocationUtil? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationUtil = LocationUtil.initLocationRequest(this)
        locationUtil?.requestLocation {
            it?.let {
                val currentLocation = LatLng(it.latitude, it.longitude)
                mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 200.toFloat()))
            }
        }
    }

    private val captureLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val capturedLocations =
                    result.data?.getParcelableArrayListExtra<Coordinate>(K.CAPTURE_LOCATION_ENTRY)
                if (capturedLocations != null) {
                    Toast.makeText(requireContext(), "Farm Coordinate Captured Successful",
                        Toast.LENGTH_LONG).show()
                    addPolyGone(mMap, capturedLocations)
                } else Toast.makeText(requireContext(), "Fail", Toast.LENGTH_LONG).show()
            } else Toast.makeText(requireContext(), "Fail", Toast.LENGTH_LONG).show()
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _bind = FragmentFarmLocationBinding.bind(view)
        bind.fabForm.setOnClickListener {
            captureLauncher.launch(Intent(requireContext(), FarmCoordinateActivity::class.java))
        }
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
    }

    private fun addPolyGone(googleMap: GoogleMap?, locations: List<Coordinate>) {
        val latlngs = locations.map { LatLng(it.latitude, it.longitude) }

//        val latlngs = listOf<LatLng>(
//            LatLng(-27.457, 153.040),
//            LatLng(-33.852, 151.211),
//            LatLng(-37.813, 144.962),
//            LatLng(-34.928, 138.599)
//        )


        // Add polygons to indicate areas on the map.
        val polygon1 = googleMap?.addPolygon(
            PolygonOptions().clickable(true).add(*latlngs.toTypedArray()).fillColor(0x50011FF)
        )
        polygon1?.points?.let {
            Toast.makeText(requireContext(), "ployGone showing", Toast.LENGTH_LONG).show()
            val latLngBounds = MapUtil.getPolygonLatLngBounds(it)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 200))
        }
        polygon1?.tag = "alpha"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bind = null
    }
}
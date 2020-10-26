package com.efhem.farmapp.ui

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.fragment.app.Fragment
import com.efhem.farmapp.R
import com.efhem.farmapp.databinding.FragmentCoordinateBinding
import com.efhem.farmapp.domain.model.Coordinate
import com.efhem.farmapp.domain.model.Farm
import com.efhem.farmapp.util.K
import com.efhem.farmapp.util.LocationUtil
import com.google.android.gms.location.LocationCallback


class FragmentCoordinate : Fragment(R.layout.fragment_coordinate), View.OnClickListener {

    private var locationUtil = LocationUtil
    private lateinit var locationCallback: LocationCallback
    var coordinate1: Coordinate? = null
    var coordinate2: Coordinate? = null
    var coordinate3: Coordinate? = null
    var coordinate4: Coordinate? = null
    var currentLocationTag = 1

    private var _bind: FragmentCoordinateBinding? = null
    val bind get() = _bind!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _bind = FragmentCoordinateBinding.bind(view)

        bind.fab1.setOnClickListener(this)
        bind.fab2.setOnClickListener(this)
        bind.fab3.setOnClickListener(this)
        bind.fab4.setOnClickListener(this)

        locationUtil.initLocationRequest(this)
        locationCallback = locationUtil.requestLocationUpdates {
            val location = it?.lastLocation
            location?.let { loc ->
                when (currentLocationTag) {
                    1 -> coordinate1 = Coordinate(loc.latitude, loc.longitude)
                    2 -> coordinate2 = Coordinate(loc.latitude, loc.longitude)
                    3 -> coordinate3 = Coordinate(loc.latitude, loc.longitude)
                    4 -> coordinate4 = Coordinate(loc.latitude, loc.longitude)
                }
            }

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _bind = null
    }

    private fun setCoordinateResult() {
        if ((coordinate1 != null) and (coordinate2 != null) and (coordinate3 != null) and (coordinate4 != null)) {
            val farmName = bind.edlFarmName.editText?.text.toString()
            if(farmName.isEmpty()){
                bind.edlFarmName.error = "Farm name is required"
                return
            } else bind.edlFarmName.error = null
            setCaptureResult(farmName,
                arrayListOf(
                    coordinate1!!,
                    coordinate2!!,
                    coordinate3!!,
                    coordinate4!!
                )
            )
        }
    }

    private fun drawPolyGon() {
        if ((coordinate1 != null) and (coordinate2 != null)) {
            bind.connect12.visibility = View.VISIBLE
        }
        if ((coordinate2 != null) and (coordinate4 != null)) {
            bind.connect24.visibility = View.VISIBLE
        }
        if ((coordinate4 != null) and (coordinate3 != null)) {
            bind.connect34.visibility = View.VISIBLE
        }
        if ((coordinate3 != null) and (coordinate1 != null)) {
            bind.connect13.visibility = View.VISIBLE
        }
    }

    private fun setCaptureResult(farmName: String, value: ArrayList<Coordinate>?) {
        if(value != null){
            val intent = Intent()
            val bundle = Bundle().apply { putParcelable(K.CAPTURE_LOCATION_ENTRY, Farm("", farmName, value)) }
            intent.putExtras(bundle)
            requireActivity().setResult(Activity.RESULT_OK, intent)
            requireActivity().finish()
        }else {
            requireActivity().setResult(Activity.RESULT_CANCELED)
            requireActivity().finish()
        }

    }

    override fun onClick(v: View?) {
        val farmName = bind.edlFarmName.editText?.text.toString()
        if(farmName.isEmpty()){
            bind.edlFarmName.error = "Farm name is required"
            return
        }else bind.edlFarmName.error = null

        when (v?.id) {
            R.id.fab_1 -> currentLocationTag = 1
            R.id.fab_2 -> currentLocationTag = 2
            R.id.fab_3 -> currentLocationTag = 3
            R.id.fab_4 -> currentLocationTag = 4
        }
        startTimer()
    }

    private fun startTimer() {
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Acquiring coordinates...")
        progressDialog.show()

        val countDownTimer = object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                when (currentLocationTag) {
                    1 -> if(coordinate1 != null){
                        bind.fab1.isEnabled = false
                    }
                    2 -> if(coordinate2 != null){
                        bind.fab2.isEnabled = false
                    }
                    3 -> if(coordinate3 != null){
                        bind.fab3.isEnabled = false
                    }
                    4 -> if(coordinate4 != null){
                        bind.fab4.isEnabled = false
                    }
                }
                drawPolyGon()
                setCoordinateResult()
//                Toast.makeText(
//                    requireContext(),
//                    "loc1 ${coordinate1.toString()}" +
//                            "loc2 ${coordinate2.toString()}" +
//                            "loc3 ${coordinate3.toString()}" +
//                            "loc4 ${coordinate4.toString()}" +
//                            "", Toast.LENGTH_SHORT
//                ).show()
                progressDialog.dismiss()
            }

        }
        countDownTimer.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        locationUtil.removeLocationUpdates(locationCallback)
    }

    override fun onPause() {
        super.onPause()
        if (::locationCallback.isInitialized) {
            locationUtil.removeLocationUpdates(locationCallback)
        }
    }
}
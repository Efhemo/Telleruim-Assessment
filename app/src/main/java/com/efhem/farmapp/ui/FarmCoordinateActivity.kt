package com.efhem.farmapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.efhem.farmapp.R
import com.efhem.farmapp.databinding.ActivityFarmCoordinateBinding
import com.efhem.farmapp.domain.repositories.Coordinate
import com.efhem.farmapp.util.LocationUtil
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FarmCoordinateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farm_coordinate)
    }
}
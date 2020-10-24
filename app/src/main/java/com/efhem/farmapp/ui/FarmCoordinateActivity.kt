package com.efhem.farmapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.efhem.farmapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FarmCoordinateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farm_coordinate)

        val location1 = findViewById<FloatingActionButton>(R.id.fab_1)
        location1.setOnClickListener { finish() }
    }
}
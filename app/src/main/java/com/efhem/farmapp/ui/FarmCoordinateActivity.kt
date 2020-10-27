package com.efhem.farmapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.efhem.farmapp.R

class FarmCoordinateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farm_coordinate)
        val backArrow = findViewById<TextView>(R.id.btn_back_arrow)
        backArrow.setOnClickListener { finish() }
    }
}
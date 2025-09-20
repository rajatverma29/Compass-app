package com.example.compass

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.os.Vibrator
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.content.edit


class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensor: Sensor

    private lateinit var vibrator: Vibrator
    private lateinit var sensorManager: SensorManager
    private lateinit var dial: ImageView
    private lateinit var coordinates: TextView
    private lateinit var needle: ImageView
    private var degree = 0f
    private lateinit var direction: TextView
    private lateinit var toggle: com.google.android.material.materialswitch.MaterialSwitch
    private lateinit var sun: ImageView
    private lateinit var moon: ImageView
    private lateinit var layout: View
    private lateinit var name: TextView
    private lateinit var north: ImageView

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        WindowInsetsControllerCompat(window, window.decorView).apply {
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            hide(WindowInsetsCompat.Type.systemBars())
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        coordinates = findViewById(R.id.coordinates)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION)!!
        direction = findViewById(R.id.direction)
        name = findViewById<TextView>(R.id.name)
        sun = findViewById(R.id.sun)
        moon = findViewById(R.id.moon)
        layout = findViewById<View>(R.id.main)
        toggle = findViewById(R.id.themeswitch)
        dial = findViewById(R.id.dial)
        needle = findViewById(R.id.needle)
        layout.setBackgroundColor(Color.BLACK)
        direction.setTextColor(Color.WHITE)
        coordinates.setTextColor(Color.WHITE)
        name.setTextColor(Color.WHITE)
        sun.setColorFilter(Color.YELLOW)
        moon.setColorFilter(Color.WHITE)
        north = findViewById(R.id.north)
        toggle.isChecked = true
        val trackDrawable = ContextCompat.getDrawable(this, R.drawable.trackborder)
        toggle.trackDrawable = trackDrawable


        val save = getSharedPreferences("theme", MODE_PRIVATE)
        val dark = save.getBoolean("dark", true)
        toggle.isChecked = dark

        if (dark) {
            darktheme()

        } else {
            lighttheme()

        }


        toggle.setOnCheckedChangeListener { _, isChecked ->

            save.edit {
                putBoolean("dark", isChecked)
            }


            if (isChecked) {
                darktheme()

            } else {
                lighttheme()


            }


        }


    }


    @SuppressLint("SetTextI18n")
    override fun onSensorChanged(event: SensorEvent?) {

        val coordinatevalue = Math.round(event!!.values[0])
        val rotatedial =
            ObjectAnimator.ofFloat(dial, "rotation", degree, (-coordinatevalue).toFloat())
        val rotateneedle =
            ObjectAnimator.ofFloat(needle, "rotation", degree, (-coordinatevalue).toFloat())
        val rotatenorth= ObjectAnimator.ofFloat(north, "rotation", degree, (-coordinatevalue).toFloat())

        rotatedial.duration = 300
        rotateneedle.duration = 300
        rotatenorth.duration = 300
        rotatenorth.start()
        rotatedial.start()
        rotateneedle.start()

        degree = -coordinatevalue.toFloat()
        coordinates.text = "$coordinatevalue\u00B0"




        if (coordinatevalue in 0..20) {
            direction.text = "North"
        } else if (coordinatevalue in 21..89) {
            direction.text = "North_East"
        } else if (coordinatevalue in 90..110) {
            direction.text = "East"
        } else if (coordinatevalue in 111..179) {
            direction.text = "South_East"
        } else if (coordinatevalue in 180..200) {
            direction.text = "South"
        } else if (coordinatevalue in 201..269) {
            direction.text = "South_West"
        } else if (coordinatevalue in 270..290) {
            direction.text = "West"
        } else if (coordinatevalue in 291..350) {
            direction.text = "North_West"
        } else {
            direction.text = "North"
        }


    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {


    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME)

    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    fun darktheme() {
        layout.setBackgroundColor(Color.BLACK)
        direction.setTextColor(Color.WHITE)
        coordinates.setTextColor(Color.WHITE)
        name.setTextColor(Color.WHITE)
        sun.setColorFilter(Color.YELLOW)
        moon.setColorFilter(Color.WHITE)
        dial.setColorFilter(Color.WHITE)



    }

    fun lighttheme() {
        layout.setBackgroundColor(Color.WHITE)
        direction.setTextColor(Color.BLACK)
        coordinates.setTextColor(Color.BLACK)
        name.setTextColor(Color.BLACK)
        sun.setColorFilter(Color.BLACK)
        moon.setColorFilter(Color.BLUE)
        dial.setColorFilter(Color.BLACK)



    }


}




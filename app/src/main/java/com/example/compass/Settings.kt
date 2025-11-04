package com.example.compass

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Settings : AppCompatActivity() {
    private lateinit var theme: TextView
    private lateinit var themebtn: ImageButton
    private lateinit var themetext: TextView
    private lateinit var back: Button
    private lateinit var headtext: TextView
    private lateinit var layout: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        theme= findViewById<TextView>(R.id.theme)
        themebtn= findViewById<ImageButton>(R.id.themebtn)
        themetext= findViewById<TextView>(R.id.themetxt)
        back= findViewById<Button>(R.id.back)
        headtext = findViewById<TextView>(R.id.settings)
        layout = findViewById<View>(R.id.main)

        var themepref= getSharedPreferences("theme", MODE_PRIVATE)
        var dark = themepref.getBoolean("dark", true)
        if(dark){
            darktheme()
        }
        else{
            lighttheme()

        }





        back.setOnClickListener {
            finish()
        }

        themebtn.setOnClickListener {
            if(themetext.text.toString()== "Light")
            {
                lighttheme()

                themepref.edit().putBoolean("dark", false).apply()



            }
            else{
               darktheme()

                themepref.edit().putBoolean("dark",true).apply()



            }
        }








    }
    private fun lighttheme(){
        themetext.setTextColor(Color.BLACK)
        themebtn.setImageResource(R.drawable.moon)
        themebtn.setColorFilter(Color.BLUE)
        theme.setTextColor(Color.BLACK)
        back.setTextColor(Color.BLACK)
        themetext.text="Dark"
        layout.setBackgroundColor(Color.WHITE)
        headtext.setTextColor(Color.BLACK)

    }
    fun darktheme(){
        themetext.setTextColor(Color.WHITE)
        themebtn.setImageResource(R.drawable.sun)
        themebtn.setColorFilter(Color.YELLOW)
        theme.setTextColor(Color.WHITE)
        back.setTextColor(Color.WHITE)
        themetext.text="Light"
        layout.setBackgroundColor(Color.BLACK)
        headtext.setTextColor(Color.WHITE)

    }

}
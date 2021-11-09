package com.example.gotamagica.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.gotamagica.R

class success_save : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_save)

        if(supportActionBar != null){
            supportActionBar!!.hide()
        }

        var doneIcon = findViewById<ImageView>(R.id.doneIcon)

       iconAnnimation(doneIcon)
    }

    private fun iconAnnimation(doneIcon : ImageView){
        doneIcon.animate().apply {
            duration = 1500
            rotationYBy(360f)
        }
    }



}
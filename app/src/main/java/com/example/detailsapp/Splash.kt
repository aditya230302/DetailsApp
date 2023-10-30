package com.example.detailsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ProgressBar

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(applicationContext,MainActivity::class.java))
        },3000)

        var progressbar = findViewById<ProgressBar>(R.id.progressBar)
        progressbar.setOnClickListener{
            startActivity(Intent(applicationContext,MainActivity::class.java))
        }
    }
}
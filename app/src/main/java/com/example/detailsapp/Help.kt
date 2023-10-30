package com.example.detailsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.ProgressBar

class Help : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.help)


        var home = findViewById<ImageView>(R.id.help1)
        home.setOnClickListener{
            startActivity(Intent(applicationContext,MainActivity::class.java))
        }
    }
}
package com.example.zamwienia

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val handler = Handler()
    private lateinit var currentTimeTextView: TextView




    //database


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val addOrderButton = findViewById<Button>(R.id.add_order_button)
        addOrderButton.setOnClickListener {
            val intent = Intent(this, NoweZamowienie::class.java)
            startActivity(intent)
        }
        currentTimeTextView = findViewById(R.id.current_date_time_text_view)
        handler.postDelayed(runnable, 0)
    }

    private val runnable = object : Runnable {
        override fun run() {
            val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
            val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
            currentTimeTextView.text = "$currentDate $currentTime"
            handler.postDelayed(this, 1000)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }
}

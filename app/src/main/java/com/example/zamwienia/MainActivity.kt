package com.example.zamwienia

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {

    private val handler = Handler()
    private lateinit var currentTimeTextView: TextView
    val db = Firebase.firestore


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

        val tVorders = findViewById<TextView>(R.id.tVorders)


        val ordersRef = FirebaseFirestore.getInstance().collection("orders")
// Pobieranie dokumentów z kolekcji "orders"
        ordersRef.get().addOnSuccessListener { documents ->
            // Tworzenie listy obiektów Order na podstawie pobranych dokumentów
            val orderList = ArrayList<Order>()
            val ordersText = StringBuilder()
            for (document in documents) {

                var order = Order(
                    "${document.data["street"]}",
                    "${document.data["houseNo"]}",
                    "${document.data["phoneNo"]}",
                    "${document.data["deliveryTime"]}",
                    "${document.data["deliveryTimeMax"]}",
                    "${document.data["deliveryTimes"]}"
                )
                orderList.add(order)

            }


            val sortedOrderList = orderList.sortedWith(compareBy<Order>{it.deliveryTimes})

            for (order in sortedOrderList) {
                ordersText.append("${order.street} ${order.houseNo} ${order.phoneNo} ${order.deliveryTime} ${order.deliveryTimeMax} \n")}
                tVorders.text = ordersText.toString()

        }
            .addOnFailureListener{ exception ->
              Log.w(TAG, "jakis blad", exception)
            }



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

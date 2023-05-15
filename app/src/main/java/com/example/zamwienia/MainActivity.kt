package com.example.zamwienia

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
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
        val ordersDisplay = hashMapOf<String, String>(
        )



//        db.collection("orders")
//            .get()
//            .addOnSuccessListener { result ->
//                for (document in result){
//                    Log.d(TAG, "${document.id} => ${document.data.get("street")}")
//                    ordersDisplay.put("street", "${document.data.get("street")}")
//                    //tVorders.setText("${document.data.get("street")}")
//                }
//                println(ordersDisplay)
//
//            }
//            .addOnFailureListener{ exception ->
//                Log.w(TAG, "jakis blad", exception)
//            }

        val ordersRef = FirebaseFirestore.getInstance().collection("orders")
// Pobieranie dokumentów z kolekcji "books"
        ordersRef.get().addOnSuccessListener { documents ->
            // Tworzenie listy obiektów Order na podstawie pobranych dokumentów
            val orderList = ArrayList<Order>()
            //to dodałem
            val ordersText = StringBuilder()
            for (document in documents) {
                //to dodałem
                val street = document.getString("street")
                val houseNo = document.getString("houseNo")
                val phoneNo = document.getString("phoneNo")
                val deliveryTime = document.getString("deliveryTime")
                val deliveryTimeMax = document.getString("deliveryTimeMax")

                var order = Order(
                    "${document.data["street"]}",
                    "${document.data["houseNo"]}",
                    "${document.data["phoneNo"]}",
                    "${document.data["deliveryTime"]}",
                    "${document.data["deliveryTimeMax"]}"
                )
                orderList.add(order)


                // Tworzenie obiektu Book na podstawie danych z dokumentu Firestore
//                order = document.toObject(Order::class.java)
//                orderList.add(order)
            }

            val sortedOrderList = orderList.sortedWith(compareBy<Order> { it.deliveryTime }.thenBy  { it.deliveryTimeMax })
            for (order in sortedOrderList) {
                // to dodałem
                ordersText.append("${order.street} ${order.houseNo} ${order.phoneNo} ${order.deliveryTime} ${order.deliveryTimeMax} \n")}
                tVorders.text = ordersText.toString()
            // Wyświetlenie listy obiektów Book
            // to na razie usunąłem
           // tVorders.setText(orderList[4].street + orderList[4].phoneNo + orderList[2].street + orderList[2].phoneNo)
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

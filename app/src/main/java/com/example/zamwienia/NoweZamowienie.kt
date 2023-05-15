package com.example.zamwienia

import android.app.TimePickerDialog
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import com.google.android.material.bottomnavigation.BottomNavigationView


class NoweZamowienie : AppCompatActivity() {

    val db = Firebase.firestore



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nowe_zamowienie)

        val addBackButton = findViewById<Button>(R.id.back_button)
        addBackButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val eTstreet = findViewById<EditText>(R.id.eTstreet)
        val eThouseNo = findViewById<EditText>(R.id.eThouseNo)
        val eTphoneNo = findViewById<EditText>(R.id.eTphoneNo)
        val eTdeliveryTimeMax = findViewById<EditText>(R.id.eTdeliveryTimeMax)
        val eTdeliveryTime = findViewById<EditText>(R.id.eTdeliveryTime)
        val ok_button = findViewById<Button>(R.id.ok_order_button)
        var deliveryTimes: String = ""


        //editText.inputType = InputType.TYPE_CLASS_DATETIME or InputType.TYPE_DATETIME_VARIATION_TIME

        eTdeliveryTimeMax.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
                cal.set(Calendar.MINUTE, minute)
                eTdeliveryTimeMax.setText(SimpleDateFormat("HH:mm").format(cal.time))
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        eTdeliveryTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
                cal.set(Calendar.MINUTE, minute)
                eTdeliveryTime.setText(SimpleDateFormat("HH:mm").format(cal.time))
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }




        // przycisk OK



        ok_button.setOnClickListener {

            if (eTdeliveryTime.length()>1)
                deliveryTimes = eTdeliveryTime.text.toString()
            else
                deliveryTimes = eTdeliveryTimeMax.text.toString()

            val order = hashMapOf(
                "deliveryTime" to eTdeliveryTime.text.toString(),
                "deliveryTimeMax" to eTdeliveryTimeMax.text.toString(),
                "houseNo" to eThouseNo.text.toString(),
                "phoneNo" to eTphoneNo.text.toString(),
                "street" to eTstreet.text.toString(),
                "deliveryTimes" to deliveryTimes
            )


            db.collection("orders")
                .add(order)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}
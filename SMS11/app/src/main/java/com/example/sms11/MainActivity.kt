package com.example.sms11

import android.app.Activity
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.telephony.SmsManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {


    // global varaibles

    private val SEND_SMS_PERMISSION_REQUEST_CODE = 1

    lateinit var sendSMSBtn: Button
    lateinit var phoneNumED: EditText
    lateinit var msgBodyed: EditText
    lateinit var resultTV: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // type casting
        sendSMSBtn = findViewById(R.id.button)

        phoneNumED = findViewById(R.id.editTextText)
        msgBodyed = findViewById(R.id.editTextText2)
        resultTV = findViewById(R.id.textView2)
        sendSMSBtn.setOnClickListener {
            val phoneNumber = phoneNumED.text.toString()
            val message = msgBodyed.text.toString()
            if (checkPermission()) {
                sendSms(phoneNumber, message)
            } else {
                requestPermission()
            }
        }


        //creating a method to sync to the buttons


    }

    // new meth
    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this, android.Manifest.permission.SEND_SMS
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {

        ActivityCompat.requestPermissions(
            this, arrayOf(android.Manifest.permission.SEND_SMS),
            SEND_SMS_PERMISSION_REQUEST_CODE
        )
    }

    // controll o and search permission request result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == SEND_SMS_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED
            ) {
                val phoneNumber = phoneNumED.text.toString()
                val message = msgBodyed.text.toString()
                //pull the method
            }//inner if
            else {
                resultTV.text = "Permissions denied"
            }
        }//outer if
    }//method end

    private fun sendSms(phoneNumber: String, message: String) {
        try {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            resultTV.text = "sms has been sent"
        } catch (e: Exception) {
            resultTV.text = "failed to send sms"
            e.printStackTrace()
        }
    }
}
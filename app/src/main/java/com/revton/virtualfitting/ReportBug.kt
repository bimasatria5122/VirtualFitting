package com.revton.virtualfitting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.revton.virtualfitting.core.Animation
import kotlinx.android.synthetic.main.activity_report_bug.*

class ReportBug : AppCompatActivity() {

    lateinit var editTextSubject: EditText
    lateinit var editTextMessage: EditText
    private val devEmail = arrayOf("bimasatria5122@gmail.com") // Recipient Email
    lateinit var subject: String
    lateinit var message: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_bug)

        editTextSubject = findViewById(R.id.bugTitle)
        editTextMessage = findViewById(R.id.bugDesc)


        buttonSend.setOnClickListener {
            Animation().buttonClicked(buttonSend) //Add animation when view clicked
            getData() //call method getData from text field

            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "message/rfc822"

            intent.putExtra(Intent.EXTRA_EMAIL, devEmail) //Add Recipient Email
            intent.putExtra(Intent.EXTRA_SUBJECT, subject)//Add subject
            intent.putExtra(Intent.EXTRA_TEXT, message)//Add message

            startActivity(Intent.createChooser(intent, "Select your email"))
        }


        back.setOnClickListener{
            Animation().buttonClicked(back) //Add animation when view clicked
            finish()
        }

    }


    //Method get data from text field//
    private fun getData() {
        subject = editTextSubject.text.toString() //Convert text field value to string
        message = editTextMessage.text.toString() //Convert text field value too string
    }
    //End Method get data from text field//


}

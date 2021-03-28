package com.revton.virtualfitting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.revton.virtualfitting.core.Animation
import kotlinx.android.synthetic.main.activity_developer_profile.*
import kotlinx.android.synthetic.main.activity_settings.back

class DeveloperProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developer_profile)

        back.setOnClickListener{
            Animation().buttonClicked(back) //Add Animation when button clicked
            finish()
        }

        github.setOnClickListener{
            Animation().buttonClicked(github) //Add Animation when button clicked
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/bimasatria5122/VirtualFitting")))
        }

        gmail.setOnClickListener{
            Animation().buttonClicked(gmail) //Add Animation when button clicked
            Toast.makeText(this, "bimasatria5122@gmail.com", Toast.LENGTH_SHORT).show() // Show popup toast message when picture saved
        }


    }
}
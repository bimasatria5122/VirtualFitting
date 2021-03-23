package com.revton.virtualfitting

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity()
{
    var progressAngka = 0
    var handler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Splash Screen Progress menggunakan Callback
        handler = Handler(Handler.Callback {
            progressAngka++
            if(progressAngka == 100) {

                finish() // Stop activity
                if (
                    ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                    &&
                    ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED )
                {
                    startActivity(Intent(this, VFCapture::class.java)) //Pindah Activity
                }

                else if (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED )
                {
                    startActivity(Intent(this, AllowCamera::class.java)) //Pindah Activity
                }

                else if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED )
                {
                    startActivity(Intent(this, AllowStorage::class.java)) //Pindah Activity
                }

                else
                {
                    startActivity(Intent(this, AllowStorage::class.java)) //Pindah Activity
                }

            }
            handler?.sendEmptyMessageDelayed(0, 10) //Delay callback function
            true
        })


        handler?.sendEmptyMessage(0)
        //End Splash Screen Progress
    }
}
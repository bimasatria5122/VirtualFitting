package com.revton.virtualfitting

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener
import com.revton.virtualfitting.core.Animation
import kotlinx.android.synthetic.main.activity_allow_camera.*

class AllowCamera : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_allow_camera)

        button_camera.setOnClickListener{
            Animation().buttonClicked(findViewById(R.id.button_camera))
            allowAccessCamera()
        }

    }



    //Meminta Perijinan Akses Kamera//
    private fun allowAccessCamera()
    {
        Dexter.withActivity(this)
            .withPermissions(android.Manifest.permission.CAMERA) //Ask Camera Access Permission
            .withListener(object : BaseMultiplePermissionsListener()
            {
                override fun onPermissionsChecked(response: MultiplePermissionsReport)
                {
                    if (response.areAllPermissionsGranted())
                    {
                        finish()
                        pindah()
                    }
                }
            }).check()
    }
    //End Meminta Perijinan Akses Kamera//



    private fun pindah()
    {
        startActivity(Intent(this, VFCapture::class.java)) //Pindah Activity VFCapture
    }

}



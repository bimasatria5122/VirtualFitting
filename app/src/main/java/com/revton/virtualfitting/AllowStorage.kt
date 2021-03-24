package com.revton.virtualfitting

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener
import com.revton.virtualfitting.core.Animation
import kotlinx.android.synthetic.main.activity_allow_storage.*

class AllowStorage : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_allow_storage)

        button_storage.setOnClickListener{
            Animation().button_clicked(findViewById(R.id.button_storage))
            allowAccessStorage()
        }

    }



    //Meminta Perijinan Akses Storage//
    private fun allowAccessStorage() = Dexter.withActivity(this)
        .withPermissions(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
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
    //End Meminta Perijinan Akses Storage//



    private fun pindah()
    {
        startActivity(Intent(this, AllowCamera::class.java)) //Pindah Activity AllowCamera
    }



}



package com.revton.virtualfitting.core

import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import java.io.File

class Config {
    private val save_path = File(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
        "Virtual Fitting"
    )

    var save_location = File(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
        "Virtual Fitting"
    )

    @RequiresApi(Build.VERSION_CODES.R)
    private val saves_paSth = File(
        Environment.getExternalStorageDirectory(),"Android/data/com.revton.virtualfitting"
    )

    fun main() {

        if(save_location == save_path)
        {
            save_location = save_path
        }
    }
}
package com.revton.virtualfitting.core

import android.content.Context
import android.os.Environment
import android.widget.Toast
import com.google.gson.Gson
import ir.androidexception.filepicker.dialog.DirectoryPickerDialog
import ir.androidexception.filepicker.interfaces.OnCancelPickerDialogListener
import ir.androidexception.filepicker.interfaces.OnConfirmDialogListener
import java.io.File

class Config {

    private val directoryPath:String? = null
    private val captureSound:Int? = null

    private val default_path = File(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
        "Virtual Fitting"
    )

    private val config_path = File(
        Environment.getExternalStorageDirectory(),
        "Android/data/com.revton.virtualfitting"
    )


    private val config_file = File(
        Environment.getExternalStorageDirectory(),
        "Android/data/com.revton.virtualfitting/VFConfig.json"
    )

    private val gson = Gson()



    //Choose Save Directory//
    public fun setDirectoryPath(context:Context) {

        val configObj = gson.fromJson(config_file.bufferedReader(),Config::class.java)

            val directoryPickerDialog = DirectoryPickerDialog(context,
                OnCancelPickerDialogListener {
                    Toast.makeText(context,
                        "Canceled!",
                        Toast.LENGTH_SHORT).show()
                },
                OnConfirmDialogListener { files: Array<File> ->

                    val configList = "{'directoryPath' : '"+files[0].path+"','captureSound'  : "+configObj.captureSound+"}"
                    config_file.writeText(configList)
                    Toast.makeText(context,
                       "Directory Changed",
                        Toast.LENGTH_SHORT).show()
                }
            )
            directoryPickerDialog.show()

    }
    //End Choose Directory//



    public fun getConfigFile():String
    {
        return config_file.toString()
    }


    public fun getConfigPath():String
    {
        return config_path.toString()
    }


    public fun getConfigSound(): Int?
    {
        val configObj = gson.fromJson(config_file.bufferedReader(),Config::class.java)
        return configObj.captureSound?.toInt()
    }


    public fun getDirectoryPath():String
    {
        val configObj = gson.fromJson(config_file.bufferedReader(),Config::class.java)
        if(configObj.directoryPath == null)
        {
            return default_path.toString()
        }
        else
        {
            return configObj.directoryPath.toString()
        }
    }


    public fun createConfigFile()
    {
        config_path.mkdirs()
        config_file.createNewFile()
        var configList = """{"directoryPath" : """"+default_path+"""","captureSound"  : 1}"""
        config_file.writeText(configList)
    }







}
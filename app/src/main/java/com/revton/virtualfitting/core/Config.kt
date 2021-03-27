package com.revton.virtualfitting.core

import android.content.Context
import android.os.Environment
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import ir.androidexception.filepicker.dialog.DirectoryPickerDialog
import ir.androidexception.filepicker.interfaces.OnCancelPickerDialogListener
import ir.androidexception.filepicker.interfaces.OnConfirmDialogListener
import java.io.File

class Config {


    //Property//
    private val directoryPath:String? = null
    private val captureSound:Boolean? = null

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

    private val gson = Gson() // Initialize GSON Class (JSON Parser)
    //End Property//




    //Method//

    //Choose Save Directory from file & folder picker library//
    public fun setDirectoryPath(context:Context, textView:TextView) {

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
                    textView.text = getDirectoryPath()
                }
            )
            directoryPickerDialog.show()

    }
    //End Choose Directory//



    //Method Set Switch Sound @param Boolean//
    public fun setSwitchSound(switchStatus:Boolean)
    {
        var configList:String

        if(switchStatus)
        {
            configList = """{"directoryPath" : """"+getDirectoryPath()+"""","captureSound"  : false}"""
            config_file.writeText(configList) // Write string config List ke file VFConfig.json
        }
        else
        {
            configList = """{"directoryPath" : """"+getDirectoryPath()+"""","captureSound"  : true}"""
            config_file.writeText(configList) // Write string config List ke file VFConfig.json
        }

    }
    //End Method Switch Sound//




    //Method Ambil Path VFConfig.json dari property config_file (Return Value String)//
    public fun getConfigFile():String
    {
        return config_file.toString()
    }
    //End Method Ambil config file Path//



    //Method Ambil Path dari property config_path (Return Value String)//
    public fun getConfigPath():String
    {
        return config_path.toString()
    }
    //End Method ambil directory config path//



    /* Method ambil status capture sound dari JSON File (Return value boolean)
    * jika bernilai true berarti sound aktif, jika bernilai false berarti sound tidak aktif
    * */
    public fun getConfigSound(): Boolean?
    {
        val configObj = gson.fromJson(config_file.bufferedReader(),Config::class.java)
        return configObj.captureSound
    }
    //End Method ambil status capture sound//




    //Method ambil path penyimpanan gambar//
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
    //End Method ambil path penyimpanan gambar//




    //Method Buat JSON Config File//
    public fun createConfigFile()
    {
        config_path.mkdirs() // Buat direktori sesuai config path
        config_file.createNewFile() // Buat file VFConfig JSON di direktori sesuai paath config
        val configList = """{"directoryPath" : """"+default_path+"""","captureSound"  : true}"""
        config_file.writeText(configList) // Write string config List ke file VFConfig.json
    }
    //End Method Buat JSON Config File//
    
    

    //End Method//




}
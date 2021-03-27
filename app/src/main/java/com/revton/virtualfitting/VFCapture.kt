package com.revton.virtualfitting

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.Bitmap
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.outsbook.libs.canvaseditor.CanvasEditorView
import com.revton.virtualfitting.core.Animation
import com.revton.virtualfitting.core.Camera2
import com.revton.virtualfitting.core.Config
import com.revton.virtualfitting.core.Converters
import com.revton.virtualfitting.model.ClothesModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_v_f_capture.*
import java.io.File
import java.util.*


class VFCapture : AppCompatActivity(), ClothesAdapter.CellClickListener
{

    private lateinit var camera2: Camera2 // Initialize Camera 2 Api
    private lateinit var clothesAdapter: ClothesAdapter
    private lateinit var canvasEditor:CanvasEditorView // Initialize Canvas Editor
    private val clothesList             = ArrayList<ClothesModel>()
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_v_f_capture)
        init() //Call Method Init

        canvasEditor = findViewById(R.id.canvasEditor) // Create canvas view

        settings.setOnClickListener{
            Animation().buttonClicked(findViewById(R.id.settings))
            startActivity(Intent(this, Settings::class.java)) //Show Activity Settings
        }


        ///Recycler View///
        val clothes    = findViewById<RecyclerView>(R.id.clothes)
        clothesAdapter              = ClothesAdapter(clothesList, this!!,this)
        val mLayoutManager          = LinearLayoutManager(applicationContext)
        mLayoutManager.orientation  = LinearLayoutManager.HORIZONTAL
        clothes.layoutManager       = mLayoutManager
        clothes.itemAnimator        = DefaultItemAnimator()
        clothes.adapter             = clothesAdapter
        prepareClothesData()
        ///End Recycler View Code///


    }




    //Method Show Cloth image to canvas editor//
    override fun onCellClickListener(cloth: ClothesModel) {
//        Toast.makeText(this,"Cell Clicked",Toast.LENGTH_SHORT).show()
        canvasEditor.removeAll()
        val klambi = getDrawable(resources.getIdentifier("@drawable/"+cloth.getId(),null,packageName))
            klambi.let{
                if (klambi != null) {
                    canvasEditor.addDrawableSticker(klambi)
                }
            }
    }
    //End Method Show Cloth image to canvas editor//




    //Method Convert Cloth Canvas to Bitmap//
    private fun getClothes(): Bitmap {
        return canvasEditor.downloadBitmap()
    }
    //End Method Convert//




    //Method Check storage & camera permissions//
    private fun init() = if (
        ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PERMISSION_GRANTED //Check Permission Camera Access
        &&
        ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PERMISSION_GRANTED /*Check Permission Storage Access*/ )
    {
        if(!File(Config().getConfigFile()).exists()) Config().createConfigFile() // Create JSON Config file in config_path (Check in Config Class)
        initCamera2Api() //Menjalankan method initCamera2Api
    }
    else //Move to AllowStorage Activity, if storage & camera permissions not granted
    {
        finish()
        startActivity(Intent(this, AllowStorage::class.java)) //Move to Activity AllowStorage
    }
    //End Method check storage & camera permissions//




    //Method Camera2API//
    private fun initCamera2Api() {

        camera2 = Camera2(this, camera_view) // Initialize camera view
        capture.setOnClickListener { v ->
            if(Config().getConfigSound()) captureSound()
            Animation().buttonClicked(findViewById(R.id.capture))
            camera2.takePhoto {
                Toast.makeText(v.context, "Saving Picture", Toast.LENGTH_SHORT).show() // Show popup toast message when saving process
                disposable = Converters.convertBitmapToFile(getClothes(),it) { file ->
                    Toast.makeText(v.context, "Saved Picture Path ${file.path}", Toast.LENGTH_SHORT).show() // Show popup toast message when picture saved
                }
            }

        }
    }
    //End Method Camera2API//




    //Method Capture Sound//
    private fun captureSound()
    {
            val sound: AudioManager = this.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            val volume: Int = sound.getStreamVolume(AudioManager.STREAM_MUSIC) // Get Music Volume
            val mediaPlayer = MediaPlayer.create(this, R.raw.shutter_sound) // Get Sound from res/raw

            //Check Music Volume//
            if (volume < 5)
            {
                Toast.makeText(this, "Please Turn Up Your Music Volume", Toast.LENGTH_SHORT).show() // Show popup toast message when volume value less than 5
            }
            else
            {
                mediaPlayer.start() // Play sound
            }

    }
    //End Method Capture Sound//




    //Camera2API Thread//
    override fun onPause() {
        camera2.close()
        super.onPause()
    }

    override fun onResume() {
        camera2.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        if (disposable != null)
            disposable!!.dispose()
        super.onDestroy()
    }
    //End Camera2API Thread//





    ///Clothes Data///

    private fun prepareClothesData() {
        var cloth   = ClothesModel("c1", "c1.png")
        clothesList.add(cloth)
        cloth       = ClothesModel("c2", "c2.png")
        clothesList.add(cloth)
        cloth       = ClothesModel("c3", "c3.png")
        clothesList.add(cloth)
        cloth       = ClothesModel("c4", "c4.png")
        clothesList.add(cloth)
        cloth       = ClothesModel("c5", "c5.png")
        clothesList.add(cloth)

        clothesAdapter.notifyDataSetChanged()
    }

    ///End Clothes Data///




}
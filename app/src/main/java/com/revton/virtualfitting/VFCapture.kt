package com.revton.virtualfitting

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
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
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


class VFCapture : AppCompatActivity(), ClothesAdapter.CellClickListener
{

    private lateinit var camera2: Camera2
    private val clothesList = ArrayList<ClothesModel>()
    private lateinit var clothesAdapter: ClothesAdapter
    private var disposable: Disposable? = null
    private lateinit var canvasEditor:CanvasEditorView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_v_f_capture)
        init() //Call Method Init
        canvasEditor = findViewById(R.id.canvasEditor)


        settings.setOnClickListener{
            Animation().button_clicked(findViewById(R.id.settings))
            startActivity(Intent(this, Settings::class.java)) //Munculkan Activity Settings
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


    override fun onCellClickListener(cloth: ClothesModel) {
        val clothImg: ImageView = findViewById(R.id.cloth)
//        Toast.makeText(this,"Cell Clicked",Toast.LENGTH_SHORT).show()
//        clothImg.setImageResource(resources.getIdentifier("@drawable/"+cloth.getId(),null,packageName))
//
//        var bitss = getDrawable(resources.getIdentifier("@drawable/"+cloth.getId(),null,packageName))
////        bitss.buildDrawingCache()
        canvasEditor.removeAll()
        val klambi = getDrawable(resources.getIdentifier("@drawable/"+cloth.getId(),null,packageName))
            klambi.let{
                if (klambi != null) {
                    canvasEditor.addDrawableSticker(klambi)
                }
            }
    }



    //Method Save Image//
    public fun compressBitmap(bitmap: Bitmap): File?
    {
        //create a file to write bitmap data

        try
        {

            val path = File(Config().getDirectoryPath())
            if (!path.exists())
                path.mkdirs()
            val picture = File(path, "VF-" + System.currentTimeMillis() + ".jpeg")
//
//            var bitss:ImageView = findViewById(R.id.cloth)
//            bitss.buildDrawingCache()
//            var cimg = bitss.getDrawingCache()
//
//
//
//
//            var cs: Bitmap? = null
//
//
//
//            cs = Bitmap.createBitmap(bitmap.width, bitmap.height, bitmap.config)
//
////            val icon = BitmapFactory.decodeFile(VFCapture().resources.getIdentifier("@drawable/c2",null,VFCapture().packageName).toString())
//            val comboImage = Canvas(cs)
//            comboImage.drawBitmap(cimg, Matrix(), null)
//            comboImage.drawBitmap(bitmap, 0f, 0f, null)
//            bitmap.recycle()
//            cimg.recycle()

            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos)
            val bitmapData = bos.toByteArray()

            //write the bytes in file
            val fos = FileOutputStream(picture)
            fos.write(bitmapData)
            fos.flush()
            fos.close()
            return picture
        }
        catch (e: IOException)
        {
            e.printStackTrace()
        }

        return null
    }
    //End Method Save Image//


    @SuppressLint("WrongViewCast")
    public fun getId(): ImageView {
        return findViewById(R.id.cloth)
    }


    private fun getClothes(): Bitmap {
        return canvasEditor.downloadBitmap()
    }

    //Create Camera View//
    private fun init() = if (
        ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PERMISSION_GRANTED //Check Permission Camera Access
        &&
        ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PERMISSION_GRANTED /*Check Permission Storage Access*/ )
    {
        if(!File(Config().getConfigPath()).exists()) Config().createConfigFile()
        initCamera2Api() //Menjalankan method initCamera2Api
    }
    else //Pindah Activity bila semua permission ditolak
    {
        finish()
        startActivity(Intent(this, AllowStorage::class.java)) //Pindah Activity AllowStorage
    }


    //Method Camera2API
    private fun initCamera2Api() {

        camera2 = Camera2(this, camera_view)
        capture.setOnClickListener { v ->
            camera2.takePhoto {
                Toast.makeText(v.context, "Saving Picture", Toast.LENGTH_SHORT).show()
                disposable = Converters.convertBitmapToFile(getClothes(),it) { file ->
                    Toast.makeText(v.context, "Saved Picture Path ${file.path}", Toast.LENGTH_SHORT).show()
                }
            }
            Animation().button_clicked(findViewById(R.id.capture))
        }
    }
    //End Method Camera2API//


    //End Create Camera View//




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
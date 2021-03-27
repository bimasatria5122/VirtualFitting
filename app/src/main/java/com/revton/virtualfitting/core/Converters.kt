package com.revton.virtualfitting.core

import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


object Converters {


    // This subscription needs to be disposed off to release the system resources primarily held for purpose.

    @JvmStatic   // this annotation is required for caller class written in Java to recognize this method as static
    fun convertBitmapToFile(cloth:Bitmap,bitmap: Bitmap, onBitmapConverted: (File) -> Unit): Disposable {
        return Single.fromCallable {
            compressBitmap(cloth,bitmap)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it != null) {
                    Log.i("convertedPicturePath", it.path)
                    onBitmapConverted(it)
                }
                else
                {
                    Log.i("Fail Converted Picture","Fail When Converted Picture, Please free some space storage")
                }
            }, { it.printStackTrace() })
    }



    //Method Save Image//
    private fun compressBitmap(cloth: Bitmap,bitmap: Bitmap): File?
    {
        //create a file to write bitmap data

        try
        {

            val path = File(Config().getDirectoryPath())
            if (!path.exists())
                path.mkdirs()
            val picture = File(path, "VF-" + System.currentTimeMillis() + ".jpeg")


            val comboImage = Canvas(bitmap)
            comboImage.drawBitmap(cloth, 0f, 0f, null)

            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()

            //Combine Photo and Cloth Picture
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



}

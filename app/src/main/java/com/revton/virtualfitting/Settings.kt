package com.revton.virtualfitting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.revton.virtualfitting.core.Animation
import com.revton.virtualfitting.core.Config
import kotlinx.android.synthetic.main.activity_settings.*

class Settings : AppCompatActivity() {

    private val captureSoundStatus:Boolean = Config().getConfigSound()
//    private var switchStatus:Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        save_chv.setOnClickListener {
            Config().setDirectoryPath(this,storage_path) // Set Save Picture Directory
        }

//        if (captureSoundStatus == 1) switchStatus = true else if(captureSoundStatus == 0) switchStatus = false // Check captureSoundStatus from getConfigSound

        capture_sound_switch.isChecked = captureSoundStatus  // Toggle Switch depend on captureSoundStatus property

        //Switch Listener for changed setting/change VFCapture.json //
        capture_sound_switch.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked)
            {
                Config().setSwitchSound(false)
            }
            else
            {
                Config().setSwitchSound(true)
            }
        }
        //End Switch Listener//

        storage_path.text = Config().getDirectoryPath().toString() //Set Text Directory Path
        back.setOnClickListener{
            Animation().buttonClicked(back) //Add Animation when button clicked
            finish()
        }
    }

}
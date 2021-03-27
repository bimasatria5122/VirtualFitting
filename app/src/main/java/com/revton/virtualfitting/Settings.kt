package com.revton.virtualfitting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.revton.virtualfitting.core.Config
import kotlinx.android.synthetic.main.activity_settings.*

class Settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        save_chv.setOnClickListener {
            Config().setDirectoryPath(this)
            storage_path.text = Config().getDirectoryPath().toString() //Set Text Directory Path
        }

        storage_path.text = Config().getDirectoryPath().toString() //Set Text Directory Path
        back.setOnClickListener{
            finish()
        }
    }

}
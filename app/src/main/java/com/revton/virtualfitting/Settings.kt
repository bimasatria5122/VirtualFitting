package com.revton.virtualfitting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.revton.virtualfitting.core.Config
import kotlinx.android.synthetic.main.activity_settings.*

class Settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        storage_path.text = Config().save_location.toString()
        back.setOnClickListener{
            finish()
        }
    }

    fun main() {

    }
}
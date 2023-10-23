package com.spx.wowalbum.ui.main

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.spx.wowalbum.R

class ModelActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_model)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.BLACK
            window.navigationBarColor = Color.BLACK
        }
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val data = intent.getSerializableExtra("data")
            if (data is ModelData) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ModelProfileFragment.newInstance(data))
                    .commitNow()
            }
        }
    }
}
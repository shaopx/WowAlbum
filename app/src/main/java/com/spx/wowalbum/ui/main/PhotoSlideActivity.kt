package com.spx.wowalbum.ui.main

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.spx.wowalbum.R

class PhotoSlideActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.BLACK
            window.navigationBarColor = Color.BLACK
        }
        setContentView(R.layout.activity_photo_slide)

        if (savedInstanceState == null) {
            val data = intent.getSerializableExtra("data")
            if (data is Product) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, PhotoSlideFragment.newInstance(data))
                    .commitNow()
            }
        }
    }
}
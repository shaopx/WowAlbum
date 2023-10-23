package com.spx.wowalbum

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.spx.wowalbum.net.OkHttpClientFactory

class MyApplication : Application(), ImageLoaderFactory {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .crossfade(true)
            .okHttpClient {
                OkHttpClientFactory().create()
            }
            .build()
    }

    companion object {
        lateinit var instance: MyApplication
    }
}
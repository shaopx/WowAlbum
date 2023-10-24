package com.spx.wowalbum

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.spx.wowalbum.net.OkHttpClientFactory
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application(), ImageLoaderFactory {

    @Inject
    lateinit var okhttpclient: OkHttpClient

    override fun onCreate() {
        instance = this
        super.onCreate()
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .crossfade(true)
            .okHttpClient {
                okhttpclient
            }
            .build()
    }

    companion object {
        lateinit var instance: MyApplication
    }
}
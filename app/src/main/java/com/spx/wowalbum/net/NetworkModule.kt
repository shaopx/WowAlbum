package com.spx.wowalbum.net

import android.app.Application
import coil.util.CoilUtils
import com.spx.wowalbum.MyApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun getOkHttpClient(app: Application) : OkHttpClient {
        return OkHttpClientFactory().create(app)
    }

    @Singleton
    @Provides
    fun coilCache(@ApplicationContext context: Application) : Cache {
        return CoilUtils.createDefaultCache(context)
    }

    @Singleton
    @Provides
    fun xiuRenApi(@ApplicationContext context: Application) : XiuRenApi {
        return return Retrofit.Builder()
            .baseUrl(XiuRenApi.BASE_URL)
            .client(OkHttpClientFactory().create(MyApplication.instance))
            .addConverterFactory(ProtoConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()
            .create(XiuRenApi::class.java)
    }
}
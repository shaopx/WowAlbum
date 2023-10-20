package com.spx.wowalbum.net

import android.graphics.Bitmap
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface XiurenApi {
    //http://newxiuren.cn/uploadfiles/xiuren/2022/20225583/2022558316.jpg
    @GET("uploadfiles/{prefix}/{albumId}/{photoId}.jpg")
    suspend fun getPhotoImage(@Path("prefix") prefix:String,@Path("albumId") albumId:String, @Path("photoId") photoId:String): Bitmap

    companion object {
        const val BASE_URL = "http://www.newxiuren.com/"
        fun getInstance(): XiurenApi {
//            return RetrofitFactory.create(BASE_URL, phoneNumber)
//                .create(XiurenApi::class.java)
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkHttpClientFactory().create())
                .addConverterFactory(ProtoConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
                .create(XiurenApi::class.java)
        }
    }
}
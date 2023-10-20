package com.spx.wowalbum.net

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.IOException

internal class ProtoResponseBodyConverter : Converter<ResponseBody, Bitmap> {

    //    override fun convert(value: ResponseBody): Bitmap? {
//        TODO("Not yet implemented")
//    }
    @Throws(IOException::class)
    override fun convert(responseBody: ResponseBody): Bitmap {
        responseBody.byteStream().use {
            try {
                return BitmapFactory.decodeStream(it)
            } catch (e: Exception) {
                throw RuntimeException(e) // Despite extending IOException, this is data mismatch.
            }
        }
    }
}
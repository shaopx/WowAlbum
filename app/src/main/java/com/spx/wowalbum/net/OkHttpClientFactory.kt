package com.spx.wowalbum.net

import android.content.Context
import androidx.annotation.NonNull
import coil.util.CoilUtils
import coil.util.CoilUtils.createDefaultCache
import com.spx.wowalbum.MyApplication
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.OkHttpClient.Builder
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject

class OkHttpClientFactory {

    @Inject
    lateinit var cache: Cache

    fun create(context: Context): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        // 不知道为何, 这个网址必须配置下面的headers才能在使用手机数据网络时连接, wifi时反而不用
        val myInterceptor =
            Interceptor { chain: Interceptor.Chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .header(
                        "Accept",
                        "image/avif,image/webp,image/apng,image/svg+xml,image/*,*/*;q=0.8"
                    )
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language", "en-US,en;q=0.9,zh-CN;q=0.8,zh;q=0.7")
                    .header("Host", "newxiuren.cn")
                    .header("Proxy-Connection", "keep-alive")
                    .header("Referer", "http://www.newxiuren.com/")
                    .header(
                        "User-Agent",
                        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36"
                    )
                    .method(original.method, original.body)
                    .build()
                chain.proceed(request)
            }
        return Builder() //                .addInterceptor(new ChuckerInterceptor(context))
            .addInterceptor(myInterceptor)
            .addInterceptor(logging)
            .cache(CoilUtils.createDefaultCache(context))
            .build()
    }
}
package com.spx.wowalbum.net;

import android.content.Context;

import com.chuckerteam.chucker.api.ChuckerInterceptor;
import com.spx.wowalbum.MyApplication;

import coil.util.CoilUtils;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpClientFactory {
    public OkHttpClient create() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        // 不知道为何, 这个网址必须配置下面的headers才能在使用手机数据网络时连接, wifi时反而不用
        Interceptor myInterceptor = chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .header("Accept", "image/avif,image/webp,image/apng,image/svg+xml,image/*,*/*;q=0.8")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language", "en-US,en;q=0.9,zh-CN;q=0.8,zh;q=0.7")
                    .header("Host", "newxiuren.cn")
                    .header("Proxy-Connection", "keep-alive")
                    .header("Referer", "http://www.newxiuren.com/")
                    .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36")
                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(request);
        };

        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(new ChuckerInterceptor(context))
                .addInterceptor(myInterceptor)
                .addInterceptor(logging)
                .cache(CoilUtils.createDefaultCache(MyApplication.instance))
                .build();
        return client;
    }
}

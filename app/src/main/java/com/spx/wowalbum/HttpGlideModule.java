package com.spx.wowalbum;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.spx.wowalbum.net.OkHttpClientFactory;

import java.io.InputStream;

@GlideModule
public class HttpGlideModule extends AppGlideModule {

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        Log.e("HttpGlideModule", "registerComponents: ....");
        registry.replace(GlideUrl.class, InputStream.class,
                new OkHttpUrlLoader.Factory(request -> new OkHttpClientFactory().create(context).newCall(request)));
    }
}
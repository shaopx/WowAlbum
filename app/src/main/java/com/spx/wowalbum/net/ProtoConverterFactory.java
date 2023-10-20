package com.spx.wowalbum.net;

import android.graphics.Bitmap;
import android.util.Log;

import org.xml.sax.Parser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public final class ProtoConverterFactory extends Converter.Factory {
    public static ProtoConverterFactory create() {
        return new ProtoConverterFactory();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        //进行条件判断，如果传进来的Type不是class，则匹配失败
        if (!(type instanceof Class<?>)) {
            return null;
        }
        //进行条件判断，如果传进来的Type不是MessageLite的实现类，则也匹配失败
        Class<?> c = (Class<?>) type;
        Log.i("ProtoConverterFactory", "responseBodyConverter: c:" + c);
        if (!Bitmap.class.isAssignableFrom((Class<?>) type)) {
            return null;
        }
//        Parser<Bitmap> parser;
        //返回一个实现了Converter的类，
        return new ProtoResponseBodyConverter();
    }
//
//    @Override
//    public Converter<?, RequestBody> requestBodyConverter(Type type,
//                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
//        //进行条件判断，如果传进来的Type不是class，则匹配失败
//        if (!(type instanceof Class<?>)) {
//            return null;
//        }
//        //进行条件判断，如果传进来的Type不是MessageLite的实现类，则也匹配失败
//        if (!MessageLite.class.isAssignableFrom((Class<?>) type)) {
//            return null;
//        }
//        return new ProtoRequestBodyConverter<>();
//    }
}
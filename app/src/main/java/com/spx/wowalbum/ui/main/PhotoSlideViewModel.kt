package com.spx.wowalbum.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PhotoSlideViewModel : ViewModel() {

    private val _photoUrls = MutableLiveData<List<String>>()
    val photoUrls: LiveData<List<String>> get() = _photoUrls

    fun updatePhotos(product: Product) {
        val newUrls = mutableListOf<String>()
        newUrls.add(product.cover_url)
        Log.i(TAG, "updatePhotos: cover:" + product.cover_url)
        appendPhotos(newUrls, product.cover_url, 120)
        _photoUrls.postValue(newUrls)
    }

    fun appendPhotos(urls: MutableList<String>, coverUrl: String, max: Int) {
        var prefix: String = coverUrl.removeSuffix("/cover.jpg")
        Log.i(TAG, "updatePhotos: prefix:" + prefix)
        val albumId = "/(\\d+)\$".toRegex().find(prefix)?.groups?.get(1)?.value
        Log.i(TAG, "updatePhotos: albumId:" + albumId)
        for (i in 1..max) {
            var id = "0" + i.toString()
            if (i > 9) {
                id = i.toString();
            }
            val newUrl = prefix + "/" + albumId + "" + id + ".jpg"
//            Log.i(TAG, "updatePhotos: newUrl:" + newUrl)
            urls.add(newUrl)
        }
    }
}
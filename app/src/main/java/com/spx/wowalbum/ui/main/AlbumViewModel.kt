package com.spx.wowalbum.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spx.wowalbum.MyApplication
import com.spx.wowalbum.db.AppDataBase
import com.spx.wowalbum.db.Photo
import com.spx.wowalbum.net.XiurenApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlbumViewModel : ViewModel() {
    // LiveData 用于观察数据变化
    private val _photoUrls = MutableLiveData<List<String>>()
    val photoUrls: LiveData<List<String>> get() = _photoUrls

    fun updatePhotos(product: Product) {
        val newUrls = mutableListOf<String>()
        newUrls.add(product.cover_url)
        Log.i(TAG, "updatePhotos: cover:" + product.cover_url)
        appendPhotos(newUrls, product.cover_url, 120)
        _photoUrls.postValue(newUrls)

        loadImageBitmap()
    }

    fun loadImageBitmap() {
        viewModelScope.launch {
            val start = System.currentTimeMillis()
            var bitmap =
                XiurenApi.getInstance().getPhotoImage("xiuren/2022", "20225583", "2022558316")
            val end = System.currentTimeMillis()
            Log.i(
                TAG,
                "loadImageBitmap: bitmap:" + bitmap.width + "x" + bitmap.height + ", use:${end - start}ms"
            )
            withContext(Dispatchers.IO) {
                workOnDatabase()
            }
        }
    }

    suspend fun workOnDatabase() {
//        AppDataBase.getDatabase(MyApplication.instance).getPhotoDAO()
//            .insertAll(Photo("", "xxx", "http", "me"))
//        AppDataBase.getDatabase(MyApplication.instance).getPhotoDAO()
//            .insertAll(Photo("1", "xxx", "http", "me"))
        var allPhotos = AppDataBase.getDatabase(MyApplication.instance).getPhotoDAO().getAllPhotos()
//        for (photo in allPhotos) {
//            Log.i(TAG, "loadImageBitmap: " + photo)
//        }
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

package com.spx.wowalbum.loader

import android.graphics.Bitmap
import com.spx.wowalbum.net.XiurenApi
import com.spx.wowalbum.ui.main.Product

class AlbumRepository {
    suspend fun getDatas(url: String): Bitmap {
        return XiurenApi.getInstance().getPhotoImage("xiuren/2022", "20225583", "2022558316")
    }
}
package com.spx.wowalbum.loader

import android.graphics.Bitmap
import com.spx.wowalbum.net.XiuRenApi

class AlbumRepository {
    suspend fun getDatas(url: String): Bitmap {
        return XiuRenApi.getInstance().getPhotoImage("xiuren/2022", "20225583", "2022558316")
    }
}
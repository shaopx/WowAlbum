package com.spx.wowalbum.ui.main

import android.content.Context
import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.spx.wowalbum.MyApplication
import com.spx.wowalbum.R
import com.spx.wowalbum.db.AppDataBase
import com.spx.wowalbum.db.Model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

val TAG = "WowAlbum"

class MainViewModel : ViewModel() {

    private var _modelList = MutableLiveData<List<ModelData>>()
    private val _dataList = MutableLiveData<List<Product>>()
    val dataList: LiveData<List<Product>> get() = _dataList
    val modelList: LiveData<List<ModelData>> get() = _modelList

    private val pageSize = 10
    private var currentPage = 0

    fun loadMoreData() {
        currentPage++
//        loadData()
    }

    public fun initModelDataList(context: Context) {
        _loadAndParseJson(context)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _updataDbModel()
            }
        }
    }

    suspend fun _updataDbModel() {
        Log.i(TAG, "_updataDbModel: enter...")
        AppDataBase.getDatabase(MyApplication.instance).getPhotoDAO().getAllModels().collect{
            Log.i(TAG, "_updataDbModel collect: it.size:"+it.size)
        }
        AppDataBase.getDatabase(MyApplication.instance).getPhotoDAO().getAllModels().onEach {
            Log.i(TAG, "_updataDbModel: it.size:"+it.size)
        }
//        for (modelData in modelList) {
//            modelData.run {
//                Log.i(TAG, "_updataDbModel: " + name + ", " + desc + ", " + link)
//                var dao = AppDataBase.getDatabase(MyApplication.instance).getPhotoDAO()
//                var list = dao.getModelByName(name)
//                if (list.isEmpty()) {
//                    dao.insertModels(
//                        Model(
//                            name,
//                            name.hashCode().toString(),
//                            0,
//                            desc,
//                            link,
//                            avatar_url
//                        )
//                    )
//                } else {
//                    Log.i(TAG, "_updataDbModel: " + name + " is exist!")
//                }
//
//            }
//        }
//
//        AppDataBase.getDatabase(MyApplication.instance).getPhotoDAO().getAllModels().run {
//            Log.i(TAG, "插入完毕 model size: " + size)
//        }
    }

    private fun _loadAndParseJson(context: Context) {
        try {
            val gson: Gson = Gson()
            val resources: Resources = context.getResources()
            val inputStream: InputStream = resources.openRawResource(R.raw.xiuren)
            val stringBuilder = StringBuilder()
            val bufferedReader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))

            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }

            val jsonString: String = stringBuilder.toString()
            val models = gson.fromJson(jsonString, ModelList::class.java).models
            _modelList.postValue(models)
            Log.i(TAG, "_loadAndParseJson: size: ${models.size}")
            var productList = generateProducts(models)
            Log.i(TAG, "_loadAndParseJson: productList.size: ${productList.size}")
            _dataList.postValue(productList)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun generateProducts(modelList: List<ModelData>): List<Product> {
        var products = modelList.flatMap {
            it.products
        }
        products.forEach {
            it.photos.add(it.cover_url)
        }
        return products.shuffled()
//        return products
    }
}

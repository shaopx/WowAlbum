package com.spx.wowalbum.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDAO {
    @Insert
    suspend fun insertAll(vararg photos: Photo)

    @Insert
    suspend fun insertModels(vararg models: Model)

    @Update
    suspend fun updateModels(vararg models: Model)

    @Delete
    suspend fun delete(photo: Photo)

    @Query("SELECT * FROM photos")
    fun getAllPhotos(): Flow<List<Photo>>

    @Query("SELECT * FROM albums")
    fun getAllAlbums(): Flow<List<Album>>

    @Query("SELECT * FROM models")
    fun getAllModels(): Flow<List<Model>>

    @Query("SELECT * FROM models where model_name like:name")
    fun getModelByName(name: String): Flow<List<Model>>

    //根据名字查数据
    @Query("select * from photos where model_name like:name")
    fun queryBookByModelName(name: String):  Flow<List<Photo>>

    @Query("select * from albums where model_name like:name")
    fun queryAlbumByModelName(name: String): Flow<List<Album>>

    @Query("select * from albums where model_name ==:id")
    fun queryAlbumByModelId(id: String): Flow<List<Album>>

    //根据专辑id
    @Query("select * from photos where album_id ==:albumId")
    fun queryPhotosByAlbumId(albumId: String): Flow<List<Photo>>
}

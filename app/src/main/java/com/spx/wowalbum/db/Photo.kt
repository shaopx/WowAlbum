package com.spx.wowalbum.db

import androidx.room.*

@Entity(
    tableName = "photos",
    primaryKeys = ["model_id", "album_id", "url"],
    indices = [Index(value = ["model_id", "album_id", "model_name"])]
)
data class Photo(
    @ColumnInfo(name = "image_hash") val imageHash: String?,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "model_name") val modelName: String,
    @ColumnInfo(name = "model_id") val modelId: String,
    @ColumnInfo(name = "album_id") val albumId: String,
)

@Entity(
    tableName = "albums",
    primaryKeys = ["model_id", "album_id"],
    indices = [Index(value = ["model_id", "album_id", "model_name"])]
)
data class Album(
    @ColumnInfo(name = "model_name") val modelName: String,
    @ColumnInfo(name = "model_id") val modelId: String,
    @ColumnInfo(name = "album_id") val albumId: String,
    @ColumnInfo(name = "album_name") val albumName: String,
)

@Entity(
    tableName = "models",
    primaryKeys = ["model_name"],
    indices = [Index(value = ["model_name"])]
)
data class Model(
    @ColumnInfo(name = "model_name") val modelName: String,
    @ColumnInfo(name = "model_id") val modelId: String,
    @ColumnInfo(name = "album_count") val albumCount: Int,
    @ColumnInfo(name = "desc") val desc: String,
    @ColumnInfo(name = "link") val link: String,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String?,
)
package com.spx.wowalbum.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.util.concurrent.CountDownLatch

/**
 * 本地数据库-->可以存放多张表
 */
@Database(
    entities = [Photo::class, Album::class, Model::class],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {

    // Student数据访问对象
    abstract fun getPhotoDAO(): PhotoDAO

    companion object {
        @Volatile
        private var Instance: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDataBase::class.java, "xiuren_db")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
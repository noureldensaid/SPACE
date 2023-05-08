package com.spc.space.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.spc.space.models.fake.UnsplashPhoto

@Database(entities = [UnsplashPhoto::class], version = 1)
abstract class UnsplashDatabase : RoomDatabase() {
    abstract fun unsplashPhotoDao(): UnsplashPhotoDao
}
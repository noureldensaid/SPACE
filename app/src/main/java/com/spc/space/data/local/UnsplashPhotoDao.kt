package com.spc.space.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.spc.space.models.fake.UnsplashPhoto

@Dao
interface UnsplashPhotoDao {

    @Query("SELECT * FROM unsplash_photo_table")
    fun getAll(): LiveData<List<UnsplashPhoto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(unsplashPhotos: List<UnsplashPhoto>)

    @Query("DELETE FROM unsplash_photo_table")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(unsplashPhoto: UnsplashPhoto)


}
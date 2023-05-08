package com.spc.space.models.fake

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
 import com.spc.space.utils.Constants.UNSPLASH_PHOTO_TABLE
import kotlinx.parcelize.Parcelize

@Entity(tableName = UNSPLASH_PHOTO_TABLE)
@Parcelize
data class UnsplashPhoto(
    @PrimaryKey
    val id: String,
    @Embedded
    val urls: Urls
) : Parcelable {

    @Parcelize
    data class Urls(
        val raw: String,
        val full: String,
        val regular: String,
        val small: String,
        val thumb: String,
    ) : Parcelable
}
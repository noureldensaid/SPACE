package com.spc.space.models.updateProfilePic

import okhttp3.RequestBody
import java.io.File

data class UpdateProfilePicRequest(
    val image: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UpdateProfilePicRequest

        if (!image.contentEquals(other.image)) return false

        return true
    }

    override fun hashCode(): Int {
        return image.contentHashCode()
    }
}

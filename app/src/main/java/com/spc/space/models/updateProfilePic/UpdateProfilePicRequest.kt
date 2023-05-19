package com.spc.space.models.updateProfilePic

import okhttp3.RequestBody
import java.io.File

data class UpdateProfilePicRequest(
    val image: RequestBody
)

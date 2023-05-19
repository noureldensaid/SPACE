package com.spc.space.models.updatePassword

data class UpdatePasswordRequest(
     val currentPassword:String,
     val newPassword :String,
     val newCPassword: String
)

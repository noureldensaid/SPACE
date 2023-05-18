package com.spc.space.models.auth.changePassword

data class ChangePasswordRequest (
    val email: String,
    val password: String,
    val OTPCode: String

)

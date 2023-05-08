package com.spc.space.models.user

data class User(
    val email: String,
    val password: String,
    val userName: String,
    val userToken: String?,
    val favorites: List<Any>
)

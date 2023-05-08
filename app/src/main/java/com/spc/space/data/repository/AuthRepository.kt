package com.spc.space.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.spc.space.data.remote.SpaceApi
import com.spc.space.models.auth.signIn.SignInRequest
import com.spc.space.models.auth.signUp.SignUpRequest
 import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AuthRepository @Inject constructor(
    private val spaceApi: SpaceApi
) {
    suspend fun signUp(request: SignUpRequest) = spaceApi.signUp(request)

    suspend fun signIn(request: SignInRequest) = spaceApi.signIn(request)
}
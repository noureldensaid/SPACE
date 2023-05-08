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
import com.spc.space.utils.Constants.USER_TOKEN
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AuthRepository @Inject constructor(
    private val context: Context,
    private val spaceApi: SpaceApi
) {
    private val Context.dataStore: DataStore<Preferences>
            by preferencesDataStore(name = "user_preferences")

    suspend fun saveToken(key: String, value: String) {
        val preferencesKey = stringPreferencesKey(key)
        context.dataStore.edit { userDataPreferences ->
            userDataPreferences[preferencesKey] = value
        }
    }

    suspend fun getToken(key: String): String? {
        return try {
            val preferencesKey = stringPreferencesKey(key)
            val userDataPreferences = context.dataStore.data.first()
            userDataPreferences[preferencesKey]
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun clearToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(stringPreferencesKey(USER_TOKEN))
        }
    }


    suspend fun signUp(request: SignUpRequest) = spaceApi.signUp(request)

    suspend fun signIn(request: SignInRequest) = spaceApi.signIn(request)


}
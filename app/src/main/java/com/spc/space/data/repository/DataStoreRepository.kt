package com.spc.space.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.spc.space.models.auth.signUp.SignUpResponse
import com.spc.space.utils.Constants.USER_INFO
import com.spc.space.utils.Constants.USER_PIC
import com.spc.space.utils.Constants.USER_TOKEN
import kotlinx.coroutines.flow.first
import java.net.URI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreRepository @Inject constructor(
    private val context: Context
) {
    companion object {
        val Context.dataStore: DataStore<Preferences>
                by preferencesDataStore(name = "user_preferences")
    }

    suspend fun saveToken(value: String) {
        val preferencesKey = stringPreferencesKey(USER_TOKEN)
        context.dataStore.edit { userDataPreferences ->
            userDataPreferences[preferencesKey] = value
        }
    }

    suspend fun getToken(): String? {
        return try {
            val preferencesKey = stringPreferencesKey(USER_TOKEN)
            val userDataPreferences = context.dataStore.data.first()
            userDataPreferences[preferencesKey]
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }




    suspend fun saveUserName(value: String) {
        val preferencesKey = stringPreferencesKey(USER_INFO)
        context.dataStore.edit { userDataPreferences ->
            userDataPreferences[preferencesKey] = value
        }
    }

    suspend fun getUserName(): String? {
        return try {
            val preferencesKey = stringPreferencesKey(USER_INFO)
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

    suspend fun saveUserInfo(value: SignUpResponse) {
        val preferencesKey = stringPreferencesKey(USER_INFO)
        context.dataStore.edit { userDataPreferences ->
            //   userDataPreferences[preferencesKey] = value
        }
    }

    suspend fun getUserInfo(): String? {
        return try {
            val preferencesKey = stringPreferencesKey(USER_INFO)
            val userDataPreferences = context.dataStore.data.first()
            userDataPreferences[preferencesKey]
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


}
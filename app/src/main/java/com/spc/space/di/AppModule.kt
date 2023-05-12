package com.spc.space.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.spc.space.data.remote.SpaceApi
import com.spc.space.data.repository.DataStoreRepository
import com.spc.space.utils.Constants.SPACE_API_BASE_URL
 import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {



    // inject retrofit
    @Singleton
    @Provides
    fun provideSpaceApi(): SpaceApi = Retrofit.Builder()
        .baseUrl(SPACE_API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(
            GsonBuilder().setLenient().create()
        ))
        .build()
        .create(SpaceApi::class.java)


    //inject the repo as it contains context reference
    @Singleton
    @Provides
    fun provideDataStoreRepository(
        @ApplicationContext context: Context,
    ): DataStoreRepository = DataStoreRepository(context)

}
package com.spc.space.di

import android.content.Context
import androidx.room.Room
import com.spc.space.data.local.UnsplashDatabase
import com.spc.space.data.remote.SpaceApi
import com.spc.space.data.remote.UnsplashApi
import com.spc.space.data.repository.AuthRepository
import com.spc.space.utils.Constants.BASE_URL
import com.spc.space.utils.Constants.SPACE_API_BASE_URL
import com.spc.space.utils.Constants.UNSPLASH_DATABASE
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
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(SpaceApi::class.java)


    // inject the repo as it contains context reference
    @Singleton
    @Provides
    fun provideDataStoreRepository(
        @ApplicationContext context: Context,
        spaceApi: SpaceApi
    ): AuthRepository = AuthRepository(
        context, spaceApi
    )


    // FAKE

    @Singleton
    @Provides
    fun provideUnsplashApi(): UnsplashApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(UnsplashApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): UnsplashDatabase =
        Room.databaseBuilder(
            context,
            UnsplashDatabase::class.java,
            UNSPLASH_DATABASE
        ).build()
}
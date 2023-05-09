package com.spc.space.di

import android.content.Context
import androidx.room.Room
import com.spc.space.data.local.UnsplashDatabase
import com.spc.space.data.remote.*
import com.spc.space.data.repository.DataStoreRepository
import com.spc.space.utils.Constants.BASE_URL
import com.spc.space.utils.Constants.SPACE_API_BASE_URL
import com.spc.space.utils.Constants.UNSPLASH_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @HttpHeaderInterceptor
    @Singleton
    @Provides
    fun provideHeaderTokenInterceptor(dataStoreRepo: DataStoreRepository): Interceptor {
//        val token = dataStoreRepo.getToken()
        return Interceptor {
            val oldRequest = it.request()

            val request = oldRequest.newBuilder().apply {
//                if (token != null)
//                    addHeader("authorization", "Bearer__$token")
            }
            it.proceed(request.build())
        }
    }

    @HttpLogginInterceptor
    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }

    @OkhttpClientQualifier
    @Singleton
    @Provides
    fun provideOkHttpClient(
        @HttpLogginInterceptor logging: HttpLoggingInterceptor,
        @HttpHeaderInterceptor tokenInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(tokenInterceptor)
            .build()

    }

    // inject retrofit
    @Singleton
    @Provides
    fun provideSpaceApi(@OkhttpClientQualifier client: OkHttpClient): SpaceApi = Retrofit.Builder()
        .baseUrl(SPACE_API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
        .create(SpaceApi::class.java)


    //inject the repo as it contains context reference
    @Singleton
    @Provides
    fun provideDataStoreRepository(
        @ApplicationContext context: Context,
    ): DataStoreRepository = DataStoreRepository(context)


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
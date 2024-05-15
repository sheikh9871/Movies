package com.example.buildProduct.di.module

import android.util.Log
import com.example.buildProduct.remoteUtils.Headers
import com.example.buildProduct.remoteUtils.LiveDataCallAdapterFactory
import com.example.buildProduct.remoteUtils.UrlHub
import com.example.buildProduct.remoteUtils.WebService
import com.example.buildProduct.remoteUtils.WebServiceHolder
import com.example.buildProduct.BuildConfig
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun webServiceHolder() = WebServiceHolder.instance

    @Provides
    @Singleton
    fun provideGson() = Gson()

    @Provides
    @Singleton
    fun provideOkHttpClient(
    ): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(150, TimeUnit.SECONDS)
            .readTimeout(150, TimeUnit.SECONDS)
            .writeTimeout(150, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val original = chain.request()
                Log.e("API", "api called - ${chain.request().url}")
                // add request headers
                val request = original.newBuilder()
                    .addHeader("X-RapidAPI-Key", "d55b31ddedmsh070cf73e145fd1ap1418e2jsn07e6cd3f954f")
                    .addHeader("X-RapidAPI-Host", "moviesdatabase.p.rapidapi.com")
                    .build()

                Log.e("HEADER", "header ${request.headers}")
                chain.proceed(request)
            }
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(UrlHub.BASE_URL)//todo
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideApiWebservice(restAdapter: Retrofit): WebService {
        val webService = restAdapter.create(WebService::class.java)
        WebServiceHolder.instance.setAPIService(webService)
        return webService
    }
}
package io.hustler.enparadignwaether.data.service

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.hustler.enparadignwaether.BuildConfig
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


object Networking {
    private const val NETWORK_CALL_TIMEOUT = 60

    fun createWeatherRemoteService(
        baseUrl: String,
        cacheDir: File,
        cacheSize: Long
    ): WeatherRestService {
        return Retrofit.Builder().baseUrl(baseUrl).client(getHttpClient(cacheDir, cacheSize))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(WeatherRestService::class.java)
    }


    fun getHttpClient(
        cacheDir: File,
        cacheSize: Long
    ): OkHttpClient {
        return OkHttpClient
            .Builder().cache(Cache(cacheDir, cacheSize))
            .addInterceptor(getInterceptor())
            .readTimeout(NETWORK_CALL_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(NETWORK_CALL_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .build()
    }

    fun getInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
}
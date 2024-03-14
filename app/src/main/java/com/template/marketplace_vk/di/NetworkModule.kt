package com.template.marketplace_vk.di

import android.content.Context
import android.net.ConnectivityManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.template.marketplace_vk.BuildConfig
import com.template.marketplace_vk.R
import com.template.marketplace_vk.data.remote.api.APIProducts
import com.template.marketplace_vk.data.remote.interceptor.NetworkInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Singleton
    @Provides
    fun provideRateController(
        retrofit: Retrofit
    ): APIProducts = retrofit.create(APIProducts::class.java)

    @Singleton
    @Provides
    fun provideOkHttpClient(connectivityManager: ConnectivityManager): OkHttpClient {
        val networkInterceptor = NetworkInterceptor(connectivityManager)
        return if (BuildConfig.DEBUG) {
            OkHttpClient.Builder()
                .addNetworkInterceptor(
                    HttpLoggingInterceptor().apply {
                        this.level = HttpLoggingInterceptor.Level.BODY
                    }
                )
                .addNetworkInterceptor(networkInterceptor)
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .build()
        } else {
            OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build()
        }
    }

    @Singleton
    @Provides
    fun provideGSONBuilder(@ApplicationContext context: Context): Gson = GsonBuilder()
        .setLenient()
        .setDateFormat(context.getString(R.string.retrofit_gson_date_format))
        .create()

    @Singleton
    @Provides
    fun provideRetrofit(
        gson: Gson, okHttpClient: OkHttpClient,
        @ApplicationContext context: Context
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(context.getString(R.string.retrofit_url))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
}

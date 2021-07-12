package com.aknrdlt.movieapp.data.api

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit
import retrofit2.converter.gson.GsonConverterFactory

const val API_KEY = "a87bc70e878ff3134468fb8ee82b360a"
const val BASE_URL = "https://api.themoviedb.org/3/"
const val POSTER_BASE_URL = "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/"

object MovieDBObject {
    fun getClient(context : Context): MovieDBInterface {
        val cacheSize = (10 * 1024 * 1024).toLong()
        val myCache = Cache(context.cacheDir, cacheSize)

        val requestInterceptor = Interceptor { chain ->

            val url = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()

            var request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            request = if (hasNetwork(context)!!)
                request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
            else
                request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()

            return@Interceptor chain.proceed(request)
        }

        val okHttpClient = OkHttpClient.Builder()
            .cache(myCache)
            .addInterceptor(requestInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieDBInterface::class.java)
    }

    private fun hasNetwork(context: Context): Boolean? {
        var isConnected: Boolean? = false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }
}
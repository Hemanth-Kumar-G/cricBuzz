package com.hemanth.cricbuzz.common

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.IOException

class NetworkConnectionInterceptor(private val context: Context) :
    Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnected()) {
            throw NoConnectivityException()
        }
        val builder: Request.Builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }

    private fun isConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivityManager.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }

}
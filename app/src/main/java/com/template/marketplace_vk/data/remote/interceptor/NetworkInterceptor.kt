package com.template.marketplace_vk.data.remote.interceptor

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.template.marketplace_vk.domain.exceptions.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor(private val connectivityManager: ConnectivityManager) : Interceptor {

    @Throws(Exception::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInternetAvailable()) {
            throw NoConnectivityException()
        }
        val request = chain.request()
        return chain.proceed(request)
    }

    private fun isInternetAvailable(): Boolean {
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities != null &&
                (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
    }
}

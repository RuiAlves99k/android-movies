package com.appsrui.movies.interceptor

import com.appsrui.movies.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.util.Properties

/**
 * Interceptor for authentication, this will add the token to every request made
 */
class AuthInterceptor : Interceptor {
    companion object {
        private const val AUTHORIZATION = "Authorization"
    }

    private fun getToken(): String {
        return BuildConfig.API_TOKEN
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val currentRequest = chain.request().newBuilder()
        currentRequest.addHeader(AUTHORIZATION, getToken())

        val newRequest = currentRequest.build()
        return chain.proceed(newRequest)
    }
}
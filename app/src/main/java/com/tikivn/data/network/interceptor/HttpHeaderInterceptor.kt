package com.tikivn.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class HttpHeaderInterceptor : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .header("Accept","application/json")
        return chain.proceed(request.build())
    }
}
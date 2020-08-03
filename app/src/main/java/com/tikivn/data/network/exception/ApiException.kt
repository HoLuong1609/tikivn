package com.tikivn.data.network.exception

import com.google.gson.Gson
import retrofit2.HttpException

class ApiException(response: retrofit2.Response<out Any>) : HttpException(response) {
    var errorResponse: ApiError

    init {
        val json = response.errorBody()?.string()
        errorResponse = try {
            if (json != null) {
                Gson().fromJson(json, ApiError::class.java)
            } else {
                ApiError()
            }
        } catch (e: Exception) {
            ApiError(message = e.message.toString())
        }
    }
}
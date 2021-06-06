package com.example.tinkofftest.network

import com.example.tinkofftest.model.ResponseEntry
import retrofit2.Response
import retrofit2.http.GET


interface PlaceholderApi {
    companion object {
        const val BASE_URL = "https://developerslife.ru/"
    }

    @GET("random?json=true")
    suspend fun getEntry() : Response<ResponseEntry>

}

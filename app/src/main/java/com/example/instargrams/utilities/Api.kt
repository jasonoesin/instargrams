package com.example.instargrams.utilities

import com.example.instargrams.responses.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
//  End Points
    @GET("search/users")
    fun searchUsers(@Query("q") query: String): Call<SearchResponse>
}
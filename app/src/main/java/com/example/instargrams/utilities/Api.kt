package com.example.instargrams.utilities

import com.example.instargrams.responses.DetailResponse
import com.example.instargrams.responses.FollowerResponse
import com.example.instargrams.responses.FollowingResponse
import com.example.instargrams.responses.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
//  End Points
    @GET("search/users")
    fun searchUsers(@Query("q") query: String, @Header("Authorization") token: String): Call<SearchResponse>

    @GET("users/{username}")
    fun userDetail(
        @Path("username") username: String
    ): Call<DetailResponse>

    @GET("users/{username}/followers")
    fun userFollowers(
        @Path("username") username: String
    ): Call<FollowerResponse>

    @GET("users/{username}/following")
    fun userFollowings(
        @Path("username") username: String
    ): Call<FollowingResponse>
}
package com.example.githubuser.retrofit

import com.example.githubuser.BuildConfig
import com.example.githubuser.activity.MainActivity
import com.example.githubuser.respone.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: ${BuildConfig.KEY}")
    fun getSearchData(
        @Query("q") q: String
    ): Call<SearchUserResponse>

    @GET("users/{username}")
    @Headers("Authorization: ${BuildConfig.KEY}")
    fun getDetailUser(
        @Path("username") username: String
    ) : Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: ${BuildConfig.KEY}")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: ${BuildConfig.KEY}")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<ItemsItem>>
}
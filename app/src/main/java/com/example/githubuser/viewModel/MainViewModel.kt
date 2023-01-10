package com.example.githubuser.viewModel

import android.app.SearchManager
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.fragment.FollowersFragment.Companion.USERNAME
import com.example.githubuser.respone.DetailUserResponse
import com.example.githubuser.respone.ItemsItem
import com.example.githubuser.respone.SearchUserResponse
import com.example.githubuser.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel(){
    private val _search = MutableLiveData<List<ItemsItem>>()
    val search: LiveData<List<ItemsItem>> = _search
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser
    private val _followers = MutableLiveData<List<ItemsItem>?>(null)
    val followers: LiveData<List<ItemsItem>?> = _followers
    private val _following = MutableLiveData<List<ItemsItem>?>(null)
    val following: LiveData<List<ItemsItem>?> = _following

    fun findItems(context: Context) {
        _isLoading.value = true
        val client = ApiConfig.getApiService(context).getSearchData(USERNAME)
        client.enqueue(object : Callback<SearchUserResponse> {
            override fun onResponse(
                call: Call<SearchUserResponse>,
                response: Response<SearchUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _search.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun searchUserData(context: Context, dataUsers: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService(context).getSearchData(dataUsers)
        client.enqueue(object : Callback<SearchUserResponse> {
            override fun onResponse(
                call: Call<SearchUserResponse>,
                response: Response<SearchUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _search.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getDetailUser(context: Context, dataUsers: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService(context).getDetailUser(dataUsers)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _detailUser.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollowersData(context: Context, dataUsers: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService(context).getFollowers(dataUsers)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _followers.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollowingData(context: Context, dataUsers: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService(context).getFollowing(dataUsers)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _following.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}
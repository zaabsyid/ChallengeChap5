package com.zahirar.challengechap5.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zahirar.challengechap5.model.*
import com.zahirar.challengechap5.network.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelUser : ViewModel() {
    lateinit var postLDUser : MutableLiveData<PostUserResponse>
    lateinit var liveGetDataUserId : MutableLiveData<GetUserResponseItem>
    lateinit var liveDataUpdateUserId : MutableLiveData<GetUserResponseItem>

    init {
        postLDUser = MutableLiveData()
        liveGetDataUserId = MutableLiveData()
        liveDataUpdateUserId = MutableLiveData()
    }

    fun postLiveDataUser(): MutableLiveData<PostUserResponse> {
        return postLDUser
    }

    fun getLiveDataUserId() : MutableLiveData<GetUserResponseItem>{
        return liveGetDataUserId
    }

    fun getliveDataUpdateUserId() : MutableLiveData<GetUserResponseItem>{
        return liveDataUpdateUserId
    }

    fun callPostApiUser(name : String, username : String, password : String){
        APIClient.instance.registerUser(
            DataUser(name,username,password)
        )
            .enqueue(object : retrofit2.Callback<PostUserResponse>{
                override fun onResponse(
                    call: retrofit2.Call<PostUserResponse>,
                    response: retrofit2.Response<PostUserResponse>
                ) {
                    if (response.isSuccessful){
                        postLDUser.postValue(response.body())
                    }else{
                        postLDUser.postValue(null)
                    }
                }

                override fun onFailure(call: retrofit2.Call<PostUserResponse>, t: Throwable) {
                    postLDUser.postValue(null)
                }

            })
    }

    fun callGetUserById(id : Int){
        APIClient.instance.getUsersById(id)
            .enqueue(object : Callback<GetUserResponseItem> {
                override fun onResponse(
                    call: Call<GetUserResponseItem>,
                    response: Response<GetUserResponseItem>
                ) {
                    if (response.isSuccessful){
                        liveGetDataUserId.postValue(response.body())
                    }else{
                        liveGetDataUserId.postValue(null)
                    }
                }

                override fun onFailure(call: Call<GetUserResponseItem>, t: Throwable) {
                    liveGetDataUserId.postValue(null)
                }

            })
    }

    fun updateApiUsers(id : Int, name : String, username : String , password: String){
        APIClient.instance.updateDataUsers(id, DataUser(name, username, password))
            .enqueue(object : Callback<GetUserResponseItem> {
                override fun onResponse(
                    call: Call<GetUserResponseItem>,
                    response: Response<GetUserResponseItem>
                ) {
                    if (response.isSuccessful){
                        liveDataUpdateUserId.postValue(response.body())
                    }else{
                        liveDataUpdateUserId.postValue(null)
                    }
                }

                override fun onFailure(call: Call<GetUserResponseItem>, t: Throwable) {
                    liveDataUpdateUserId.postValue(null)
                }

            })
    }
}
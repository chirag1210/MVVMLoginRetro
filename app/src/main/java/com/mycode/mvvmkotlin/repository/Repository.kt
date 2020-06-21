package com.mycode.mvvmkotlin.repository

import androidx.lifecycle.MutableLiveData
import com.mycode.mvvmkotlin.model.LoginResponse
import com.mycode.mvvmkotlin.model.User
import com.mycode.mvvmkotlin.network.RetrofitClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Repository private constructor() {
    var loginLiveData = MutableLiveData<String>()

    companion object {
        private var mInstance: Repository? = null
        fun getInstance(): Repository {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance =
                        Repository()
                }
            }
            return mInstance!!
        }
    }

    fun onLogin(user: User): MutableLiveData<String> {
        RetrofitClient.api.doLogin(user).enqueue(object : Callback<LoginResponse> {
            override fun onFailure(callList: Call<LoginResponse>, t: Throwable) {
                loginLiveData.postValue("Failure ${t.printStackTrace()}")
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    loginLiveData.postValue("Login Success" + response.body().toString())
                } else {
                    val data = response.errorBody()!!.string()
                    try {
                        val jObjError = JSONObject(data)
                        val error = jObjError.getString("error")
                        loginLiveData.postValue("Login failed $error")
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        })
        return loginLiveData
    }
}
package com.mycode.mvvmkotlin.viewmodel

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mycode.mvvmkotlin.Util.SingleLiveEvent
import com.mycode.mvvmkotlin.Util.Util
import com.mycode.mvvmkotlin.model.User
import com.mycode.mvvmkotlin.repository.Repository

class MainViewModel(application: Application) : AndroidViewModel(application) {

    var btnSelected: ObservableBoolean? = null
    var email: ObservableField<String>? = null
    var password: ObservableField<String>? = null
    var progressDialog: SingleLiveEvent<Boolean>? = null
    var mRepository: Repository? = null
    var loginLiveData: MutableLiveData<String>? = MutableLiveData<String>()


    init {
        btnSelected = ObservableBoolean(false)
        progressDialog = SingleLiveEvent<Boolean>()
        email = ObservableField("")
        password = ObservableField("")
        mRepository = Repository.getInstance()
    }

    fun onEmailChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        btnSelected?.set(Util.isEmailValid(s.toString()) && password?.get()!!.length >= 8)
    }

    fun onPasswordChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        btnSelected?.set(Util.isEmailValid(email?.get()!!) && s.toString().length >= 8)
    }


    fun onLogin(): MutableLiveData<String>? {
        loginLiveData =
            mRepository?.onLogin(User(email?.get().toString(), password?.get().toString()))
        //object : Callback<ChannelsData>
        return loginLiveData
    }
}
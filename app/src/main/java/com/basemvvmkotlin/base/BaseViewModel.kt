package com.basemvvmkotlin.base

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.basemvvmkotlin.R


/**
 * Created by Darshna Desai on 19/12/18.
 */
open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    val errorMessage: MutableLiveData<Int> = MutableLiveData()

    fun onApiStart() {
        //  loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    fun onInternetError() {
        //  loadingVisibility.value = View.GONE
        errorMessage.value = R.string.msg_no_internet
    }

    fun onApiFinish() {
        //  loadingVisibility.value = View.GONE
    }
}
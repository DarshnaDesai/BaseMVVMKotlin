package com.basemvvmkotlin.ui.login

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.basemvvmkotlin.R
import com.basemvvmkotlin.base.BaseViewModel
import com.basemvvmkotlin.data.local.prefs.Prefs
import com.basemvvmkotlin.data.model.api.BaseResponse
import com.basemvvmkotlin.data.remote.ApiService
import com.basemvvmkotlin.utils.AppUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LoginViewModel(application: Application) : BaseViewModel(application) {

    lateinit var apiService: ApiService

   /* constructor(apiService: ApiService) : this(getApplication()) {
        this.apiService = apiService
    }*/

    private val userViewModel: MutableLiveData<BaseResponse> by lazy {
        MutableLiveData<BaseResponse>()
    }
    private lateinit var subscription: Disposable

    fun getUserModel(): LiveData<BaseResponse> {
        return userViewModel
    }

    fun onTextChange(data: CharSequence) {
        Log.d("onTextChange", "= $data")
    }

    fun getData(prefs: Prefs, email: String) {
        if (AppUtils.hasInternet(getApplication())) {
            subscription = apiService
                    .apiGet()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe { onApiStart() }
                    .doOnTerminate { onApiFinish() }
                    .subscribe({ handleConfigResponse(it) }, this::handleConfigError)
        } else {
            onInternetError()
        }
    }

    private fun handleConfigResponse(response: BaseResponse) {
        Log.d("response", response.message)
        userViewModel.value = response
    }

    private fun handleConfigError(error: Throwable) {
        val baseResponse = BaseResponse()
        baseResponse.status = false
        baseResponse.msgId = R.string.app_name

        userViewModel.value = baseResponse
        Log.d("response error", error.localizedMessage)
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }


}
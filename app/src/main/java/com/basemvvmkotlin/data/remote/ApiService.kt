package com.basemvvmkotlin.data.remote

import com.basemvvmkotlin.data.model.api.BaseResponse
import io.reactivex.Observable
import retrofit2.http.GET


interface ApiService {

    @GET("test=123")
    fun apiGet(): Observable<BaseResponse>

}
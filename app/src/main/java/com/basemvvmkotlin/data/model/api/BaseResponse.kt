package com.basemvvmkotlin.data.model.api

import com.google.gson.annotations.SerializedName


class BaseResponse {

    @SerializedName("status")
    var status: Boolean = false
    @SerializedName("msg")
    var message: String = ""

    var msgId : Int = -1

}
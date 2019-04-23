package com.basemvvmkotlin.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by Darshna Desai on 19/12/18.
 */
object AppUtils {
    /**
     * A method which returns the state of internet connectivity of user's phone.
     */
    fun hasInternet(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

}
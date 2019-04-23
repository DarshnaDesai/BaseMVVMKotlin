package com.basemvvmkotlin.data.local.prefs

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context) {

    private val SP_NAME = Prefs::class.java.name
    private var sharedPreferences: SharedPreferences? = null

    init {
        sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
    }

    var isLoggedIn: Boolean
        set(value) = sharedPreferences!!.edit().putBoolean("key", value).apply()
        get() = sharedPreferences!!.getBoolean("key", false)

    companion object {
        var prefs: Prefs? = null

        fun getInstance(context: Context): Prefs? {
            prefs = if (prefs != null) prefs else Prefs(context)
            return prefs
        }
    }
}
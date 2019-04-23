package com.basemvvmkotlin

import com.basemvvmkotlin.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication


/**
 * Created by Darshna Desai on 26/12/18.
 */
class BaseMVVMKotlin: DaggerApplication(){
    override fun applicationInjector(): AndroidInjector<out DaggerApplication>? {
        return DaggerAppComponent.builder().create(this)
    }
}
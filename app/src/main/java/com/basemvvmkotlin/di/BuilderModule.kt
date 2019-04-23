package com.basemvvmkotlin.di

import com.basemvvmkotlin.ui.login.LoginFragment
import com.basemvvmkotlin.ui.login.LoginModule
import com.basemvvmkotlin.ui.login.LoginViewModel
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuilderModule {

    @ContributesAndroidInjector(modules = [LoginModule::class])
    abstract fun bindLoginFragment(): LoginFragment

}
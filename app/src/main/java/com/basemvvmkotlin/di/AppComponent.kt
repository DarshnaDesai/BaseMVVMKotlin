package com.basemvvmkotlin.di

import com.basemvvmkotlin.BaseMVVMKotlin
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Component(modules = [AndroidSupportInjectionModule::class, BuilderModule::class, AppModule::class])
interface AppComponent : AndroidInjector<BaseMVVMKotlin> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<BaseMVVMKotlin>()
}

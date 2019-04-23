package com.basemvvmkotlin.ui

import android.annotation.SuppressLint
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.basemvvmkotlin.R
import com.basemvvmkotlin.databinding.TempBinding
import com.basemvvmkotlin.ui.login.LoginActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {

    val SPLASH_TIME: Long = 2000

  //  private lateinit var binding : ActivitySplashBinding
    private lateinit var binding : TempBinding

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.temp)
        binding = DataBindingUtil.setContentView(this, R.layout.temp)
        //binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

      //  binding.ivSubject.outlineProvider = ZoftinoCustomOutlineProvider(160)
     //   binding.ivSubject.clipToOutline = true

        // start login activity after splash delay
        Observable.timer(SPLASH_TIME, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    //startApp()
                }
    }

    private fun startApp() {
        startActivity(LoginActivity.newIntent(applicationContext))
    }
}

package com.basemvvmkotlin.base

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Dialog
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import com.basemvvmkotlin.R
import com.basemvvmkotlin.utils.AppUtils

/**
 * Created by Darshna Desai on 17/12/18.
 */
abstract class BaseFragment<T : BaseViewModel> : Fragment() {

    private lateinit var mViewModel: T
    private val progressDialog: Dialog by lazy { Dialog(context!!) }
    private var errorSnackbar: Snackbar? = null
    private val errorClickListener = View.OnClickListener { internetErrorRetryClicked() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = getViewModel()

        mViewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })
    }

    private fun showError(@StringRes errorMessage: Int) {
        errorSnackbar = Snackbar.make(this.view!!, errorMessage, Snackbar.LENGTH_LONG)
        errorSnackbar?.setAction(R.string.action_retry, errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract fun getViewModel(): T

    abstract fun internetErrorRetryClicked()

    /* [START] show progress bar*/
    @SuppressLint("InflateParams")
    fun showProgress() {

        // dialog without title
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        // inflating and seeting view of custom dialog
        val view = LayoutInflater.from(context).inflate(R.layout.app_loading_dialog, null, false)
        val imageView = view.findViewById<ImageView>(R.id.imageView2)

        // applying rotate animation
        ObjectAnimator.ofFloat(imageView, View.ROTATION, 360f, 0f).apply {
            repeatCount = ObjectAnimator.INFINITE
            duration = 1500
            interpolator = LinearInterpolator()
            start()
        }
        progressDialog.setContentView(view)

        // setting background of dialog as transparent
        val window = progressDialog.window
        window?.setBackgroundDrawable(ContextCompat.getDrawable(context!!, android.R.color
                .transparent))

        // preventing outside touch and setting cancelable false
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()
    }
    /* [END] show progress bar*/

    fun hideProgress() {
        progressDialog.dismiss()
    }

    /* [START] Check if an active internet connection is present or not*/
    /* return boolean value true or false */
    fun isInternetAvailable(): Boolean {
        // getting Connectivity service as ConnectivityManager
        return AppUtils.hasInternet(context!!)
    }
}
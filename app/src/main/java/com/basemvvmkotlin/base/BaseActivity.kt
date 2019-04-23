package com.basemvvmkotlin.base

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Dialog
import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.PersistableBundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import com.basemvvmkotlin.R
import com.basemvvmkotlin.databinding.AppToolbarBinding
import com.basemvvmkotlin.utils.AppUtils


@SuppressLint("Registered")


/**
 * Created by Darshna Desai on 5/12/18.
 */
abstract class BaseActivity<T : BaseViewModel> : AppCompatActivity() {

    private lateinit var mViewModel: T
    private val progressDialog: Dialog by lazy { Dialog(this) }
    lateinit var toolbarBinding: AppToolbarBinding
    private var errorSnackbar: Snackbar? = null
    private val errorClickListener = View.OnClickListener { internetErrorRetryClicked() }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        mViewModel = getViewModel()

        mViewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })
        init()
    }

    // initializing application toolbar
    fun init() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        // getting toolbar binding
        toolbarBinding = DataBindingUtil.getBinding<AppToolbarBinding>(toolbar) as AppToolbarBinding
    }

    private fun showError(@StringRes errorMessage: Int) {
        errorSnackbar = Snackbar.make(window.decorView.rootView, errorMessage, Snackbar.LENGTH_LONG)
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

    fun hideToolbar() {
        toolbarBinding.toolbar.visibility = View.GONE
    }

    // toolabr title
    fun setToolbarTitle(title: Int) {
        toolbarBinding.tvToolbarTitle.text = resources.getString(title)
    }

    // set icon of toolbar left icon
    fun setToolbarLeftIcon(resourceId: Int) {
        toolbarBinding.ivToolbarLeft.setImageResource(resourceId)
        toolbarBinding.ivToolbarLeft.visibility = View.VISIBLE
        toolbarBinding.ivToolbarLeft.setOnClickListener({ onBackPressed() })
    }

    // set toolbar right icon and implement its click
    fun setToolbarRightIcon(resourceId: Int, toolbarRightClickListener: ToolbarRightImageClickListener) {
        toolbarBinding.ivToolbarRight.setImageResource(resourceId)
        toolbarBinding.ivToolbarRight.visibility = View.VISIBLE

        toolbarBinding.ivToolbarRight.setOnClickListener(
                { toolbarRightClickListener.onRightImageClicked() })
    }

    // hide toolbar right icon when not needed
    fun hideToolbarRightIcon() {
        toolbarBinding.ivToolbarRight.visibility = View.GONE
    }

    // hide toolbar left icon when not needed
    fun hideToolbarLeftIcon() {
        toolbarBinding.ivToolbarLeft.visibility = View.GONE
    }
    /* [END] Check if an active internet connection is present or not*/

    /* [START] show progress bar*/
    @SuppressLint("InflateParams")
    fun showProgress() {
        //progressDialog = Dialog(this)
        // dialog without title
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        // inflating and seeting view of custom dialog
        val view = LayoutInflater.from(this).inflate(R.layout.app_loading_dialog, null, false)
        val imageView = view.findViewById<ImageView>(R.id.imageView2)
        // applying rotate animation
        //imageView.pivotX = 0.5f
        ///imageView.pivotY = 0.5f
        ObjectAnimator.ofFloat(imageView, View.ROTATION, 360f, 0f).apply {
            repeatCount = ObjectAnimator.INFINITE
            duration = 1500
            interpolator = LinearInterpolator()
            start()
        }
        progressDialog.setContentView(view)

        // setting background of dialog as transparent
        val window = progressDialog.window
        window?.setBackgroundDrawable(ContextCompat.getDrawable(this, android.R.color.transparent))
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
    /* return boolen value true or false */
    fun isInternetAvailable(): Boolean {
        // getting Connectivity service as ConnectivtyManager
        return AppUtils.hasInternet(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        progressDialog.dismiss()
    }

    // interface class for toolbar right icon click
    interface ToolbarRightImageClickListener {
        fun onRightImageClicked()
    }
}
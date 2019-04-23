package com.basemvvmkotlin.ui.login

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.basemvvmkotlin.base.BaseFragment
import com.basemvvmkotlin.data.local.prefs.Prefs
import com.basemvvmkotlin.databinding.FragmentLoginBinding
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * Created by Darshna Desai on 17/12/18.
 */
class LoginFragment : BaseFragment<LoginViewModel>() {

    @Inject
    lateinit var prefs: Prefs

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.frag = this
        return binding.root
    }

    companion object {
        private var fragment: LoginFragment? = null

        fun newInstance(): Fragment {
            if (fragment == null) fragment = LoginFragment()
            return fragment!!
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefs.isLoggedIn = true
        viewModel.getUserModel().observe({ this@LoginFragment.lifecycle }, { userModel ->
            if (userModel != null) Log.d("user", userModel.message)
        })
    }

    override fun getViewModel(): LoginViewModel {
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        return viewModel
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    fun onSubmit() {
        viewModel.getData(prefs, "Hello")
    }

    override fun internetErrorRetryClicked() {
        viewModel.getData(prefs, "Hello")
    }

}
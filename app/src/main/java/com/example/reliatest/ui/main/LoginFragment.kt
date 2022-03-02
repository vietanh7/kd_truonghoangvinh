package com.example.reliatest.ui.main

import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.reliatest.MainApplication
import com.example.reliatest.R
import com.example.reliatest.base.BaseFragment
import com.example.reliatest.databinding.FragmentLoginBinding
import com.example.reliatest.ext.handleErrorAuth
import com.example.reliatest.param.LoginParam
import com.example.reliatest.utils.PopupUtil
import com.example.reliatest.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : BaseFragment<FragmentLoginBinding>(), View.OnClickListener {
    override val layoutId: Int
        get() = R.layout.fragment_login

    private val viewModel: LoginViewModel by viewModel()

    override fun initViews() {
        binding.fragment = this
    }

    override fun initObservers() {
        viewModel.loginLiveData.observe(this) {
            MainApplication.instance.setToken(it?.token)
            findNavController().popBackStack()
        }
    }

    private fun handleLogin() {
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString()
        val error = context?.handleErrorAuth(email, password)
        error?.let {
            PopupUtil.showPopupError(error)
            return@handleLogin
        }
        viewModel.login(LoginParam(email, password))
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnLogin -> handleLogin()
        }
    }
}
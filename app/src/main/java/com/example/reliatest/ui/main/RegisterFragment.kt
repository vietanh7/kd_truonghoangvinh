package com.example.reliatest.ui.main

import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.reliatest.R
import com.example.reliatest.base.BaseFragment
import com.example.reliatest.databinding.FragmentRegisterBinding
import com.example.reliatest.ext.handleErrorAuth
import com.example.reliatest.param.RegisterParam
import com.example.reliatest.utils.PopupUtil
import com.example.reliatest.viewmodel.RegisterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(), View.OnClickListener {
    override val layoutId: Int
        get() = R.layout.fragment_register

    private val viewModel: RegisterViewModel by viewModel()

    override fun initViews() {
        binding.fragment = this
    }

    override fun initObservers() {
        viewModel.registerLiveData.observe(this) {
            Toast.makeText(activity, "${it?.message}", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }

    private fun handleRegister() {
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString()
        val error = context?.handleErrorAuth(email, password)
        error?.let {
            PopupUtil.showPopupError(error)
            return@handleRegister
        }
        viewModel.register(RegisterParam(email, password))
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnRegister -> handleRegister()
        }
    }
}
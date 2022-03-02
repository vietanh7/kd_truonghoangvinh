package com.example.reliatest.ui.demo

import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import com.example.reliatest.MainApplication
import com.example.reliatest.R
import com.example.reliatest.base.BaseFragment
import com.example.reliatest.constant.Constant.DEFAULT_EMAIL
import com.example.reliatest.constant.Constant.DEFAULT_FCM_TOKEN
import com.example.reliatest.constant.Constant.DEFAULT_PASSWORD
import com.example.reliatest.databinding.FragmentDemoBinding
import com.example.reliatest.ext.gone
import com.example.reliatest.param.LoginParam
import com.example.reliatest.utils.PopupUtil
import com.example.reliatest.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class DemoFragment : BaseFragment<FragmentDemoBinding>(), View.OnClickListener {

    override val layoutId: Int
        get() = R.layout.fragment_demo

    override val toolbarLayoutId: Int
        get() = R.layout.layout_toolbar

    private val viewModel: LoginViewModel by viewModel()

    override fun toolbarFunc(curActivity: AppCompatActivity?, toolbar: Toolbar?) {
        toolbar?.run {
            findViewById<AppCompatTextView>(R.id.tvTitle)?.text = getString(R.string.demo1)
            findViewById<AppCompatImageButton>(R.id.btnBack)?.gone()
        }
    }

    override fun initViews() {
        binding.fragment = this
    }

    override fun initObservers() {
        viewModel.loginLiveData.observe(this) {
            MainApplication.instance.setCurrentUser(it)
            startActivity(Intent(activity, HomeDemoActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            })
            activity?.finish()
        }

        viewModel.errorLiveData.observe(this) {
            PopupUtil.showPopupError(it.first)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnLogin -> {
                viewModel.login(LoginParam(DEFAULT_EMAIL, DEFAULT_PASSWORD, DEFAULT_FCM_TOKEN))
            }
        }
    }


}
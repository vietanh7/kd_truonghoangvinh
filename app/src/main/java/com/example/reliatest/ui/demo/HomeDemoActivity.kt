package com.example.reliatest.ui.demo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.reliatest.MainApplication
import com.example.reliatest.R
import com.example.reliatest.base.BaseActivity
import com.example.reliatest.databinding.ActivityHomeDemoBinding
import com.example.reliatest.utils.PopupUtil
import com.example.reliatest.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeDemoActivity : BaseActivity<ActivityHomeDemoBinding>(), View.OnClickListener {

    override val layoutId: Int
        get() = R.layout.activity_home_demo

    private val viewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.activity = this
        binding.tvWelcome.text =
            getString(R.string.welcome, MainApplication.instance.user?.username)
    }

    override fun initObservers() {
        viewModel.feedLiveData.observe(this) {
            Log.d("vinhne", "$it")
        }
        viewModel.networkError.observe(this) {
            PopupUtil.showPopupError(it.first)
        }
//        viewModel.getFeeds()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnLogout -> {
//                MainApplication.instance.clearPref()
                startActivity(Intent(this, DemoActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                })
                finish()
            }
        }
    }
}
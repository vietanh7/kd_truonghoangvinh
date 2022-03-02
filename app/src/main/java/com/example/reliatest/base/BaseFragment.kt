package com.example.reliatest.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.reliatest.constant.Constant.DEFAULT_TOOLBAR_ID
import com.example.reliatest.ext.addToolbar

abstract class BaseFragment<Binding : ViewDataBinding> : Fragment() {

    lateinit var binding: Binding

    abstract val layoutId: Int

    open val toolbarLayoutId: Int = DEFAULT_TOOLBAR_ID

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this

        initToolbar()
        initViews()
        initObservers()
        initEventListeners()
    }

    private fun initToolbar(toolbarLayoutId: Int = this.toolbarLayoutId) {
        if (toolbarLayoutId == DEFAULT_TOOLBAR_ID) {
            (activity as? BaseActivity<*>)?.removeToolbar()
            return
        }
        (activity as? BaseActivity<*>)?.addToolbar(
            toolbarLayoutId = toolbarLayoutId,
            viewGroup = (activity as? BaseActivity<*>)?.binding?.root as? ViewGroup,
            toolbarCallback = { curActivity, toolbar ->
                toolbarFunc(curActivity, toolbar)
            }
        )
    }

    open fun toolbarFunc(curActivity: AppCompatActivity?, toolbar: Toolbar?) {}

    open fun initViews() {}

    open fun initEventListeners() {}

    open fun initObservers() {}
}
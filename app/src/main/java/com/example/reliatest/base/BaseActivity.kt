package com.example.reliatest.base

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.example.reliatest.constant.Constant
import com.example.reliatest.ext.addToolbar
import com.example.reliatest.utils.AppEvent
import com.example.reliatest.utils.PopupEventListener
import com.example.reliatest.vo.PopUp
import com.example.reliatest.widget.BottomPopupDialog
import com.example.reliatest.widget.PopupDialog
import java.util.concurrent.CopyOnWriteArraySet

abstract class BaseActivity<Binding: ViewDataBinding>: AppCompatActivity(), PopupEventListener {

    lateinit var binding: Binding

    abstract val layoutId: Int

    private var listPopupDialogFragment: Set<DialogFragment> = CopyOnWriteArraySet()

    open val toolbarLayoutId: Int = Constant.DEFAULT_TOOLBAR_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppEvent.registerPopupEventListener(this)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this

        initToolbar()
        initViews()
        initObservers()
    }

    private fun initToolbar(toolbarLayoutId: Int = this.toolbarLayoutId) {
        if (toolbarLayoutId == Constant.DEFAULT_TOOLBAR_ID) return
        addToolbar(
            toolbarLayoutId = toolbarLayoutId,
            viewGroup = binding.root as? ViewGroup,
            toolbarCallback = { curActivity, toolbar ->
                toolbarFunc(curActivity, toolbar)
            }
        )
    }

    override fun onShowPopup(popup: PopUp?) {
        onClosePopup()
        val popupDialogFragment = if (popup?.isBottomSheet == true)
            BottomPopupDialog.newInstance(popup) else PopupDialog.newInstance(popup)
        popupDialogFragment.show(supportFragmentManager, PopupDialog().tag)
        listPopupDialogFragment = listPopupDialogFragment.plus(popupDialogFragment)
    }

    override fun onClosePopup() {
        for (item in listPopupDialogFragment) {
            item.dismissAllowingStateLoss()
            listPopupDialogFragment = listPopupDialogFragment.minus(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        AppEvent.unregisterPopupEventListener(this)
    }

    open fun toolbarFunc(curActivity: AppCompatActivity?, toolbar: Toolbar?) {}

    open fun initViews(){}

    open fun initObservers(){}
}
package com.example.reliatest.utils

import androidx.databinding.ViewDataBinding
import com.example.reliatest.R
import com.example.reliatest.databinding.LayoutPopupBinding
import com.example.reliatest.listener.PopUpListener
import com.example.reliatest.vo.PopUp

object PopupUtil {
    fun showLoading() {
        AppEvent.notifyShowPopUp()
    }

    fun hideAllPopup() {
        AppEvent.notifyClosePopUp()
    }

    fun showPopupError(msg: String) {
        AppEvent.notifyShowPopUp(
            PopUp(
                R.layout.layout_popup,
                callback = { binding: ViewDataBinding?, _, _ ->
                    (binding as? LayoutPopupBinding)?.apply {
                        title = "Error"
                        message = msg
                        center = "Close"
                        clickListener = PopUpListener(
                            clickCenterListener = { AppEvent.notifyClosePopUp() }
                        )
                    }
                }
            )
        )
    }
}
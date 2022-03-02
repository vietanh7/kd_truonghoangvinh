package com.example.reliatest.utils

import com.example.reliatest.vo.PopUp
import java.util.concurrent.CopyOnWriteArraySet

object AppEvent {

    private var popupEventListeners: Set<PopupEventListener> = CopyOnWriteArraySet()

    fun registerPopupEventListener(listener: PopupEventListener?) {
        popupEventListeners = popupEventListeners.plus(listener ?: return)
    }

    fun unregisterPopupEventListener(listener: PopupEventListener?) {
        popupEventListeners = popupEventListeners.minus(listener ?: return)
    }

    fun notifyShowPopUp(popup: PopUp? = null) { // default show loading
        for (listener in popupEventListeners)
            listener.onShowPopup(popup)
    }

    fun notifyClosePopUp() {
        for (listener in popupEventListeners)
            listener.onClosePopup()
    }

}

interface PopupEventListener {
    fun onShowPopup(popup: PopUp?)
    fun onClosePopup()
}
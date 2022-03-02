package com.example.reliatest.listener

class PopUpListener(
    val clickCenterListener: () -> Unit = {},
    val clickLeftListener: () -> Unit = {},
    val clickRightListener: () -> Unit = {}
) {
    fun onClickCenterListener() = clickCenterListener()
    fun onClickLeftListener() = clickLeftListener()
    fun onClickRightListener() = clickRightListener()
}
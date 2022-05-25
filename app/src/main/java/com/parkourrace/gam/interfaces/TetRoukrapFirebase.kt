package com.parkourrace.gam.interfaces

interface TetRoukrapFirebase {
    fun getTetRoukrapUrl(callback:(String) -> Unit)
    fun getTetRoukrapSwitch(callback:(Boolean) -> Unit)
}
package com.parkourrace.gam.interfaces

interface TetRoukrapGist {
   suspend fun getTetRoukrapUrlSwitch(callback:(String, Boolean)  -> Unit)
}
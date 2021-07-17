package com.freeman.project.views

//在各總情境下使用action interface來做溝通
interface OnAction {
    fun onAction(actionID:Int,data:Any?)
}
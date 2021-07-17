package com.freeman.project.http

interface OnApiFinish<T> {
    fun onApiFinish(msg:Int,dataObj:T)
}
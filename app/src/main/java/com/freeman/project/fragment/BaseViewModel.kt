package com.freeman.project.fragment

import androidx.lifecycle.ViewModel
import com.freeman.project.data.json.BaseJson
import com.freeman.project.http.SingleLiveEvent

open class BaseViewModel:ViewModel() {

    val loadingLiveData = SingleLiveEvent<Boolean>()
    val apiErrMsg = SingleLiveEvent<String>()

    protected fun startLoading(){
        loadingLiveData.postValue(true)
    }

    protected fun stopLoading(){
        loadingLiveData.postValue(false)
    }

    protected fun showApiErrMsg(baseJson: BaseJson){
        apiErrMsg.postValue("(${baseJson.status})資料錯誤")
    }
}
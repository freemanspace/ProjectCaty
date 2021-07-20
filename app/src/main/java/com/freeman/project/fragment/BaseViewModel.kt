package com.freeman.project.fragment

import androidx.lifecycle.ViewModel
import com.freeman.project.data.json.BaseJson
import com.freeman.project.http.SingleLiveEvent
import kotlinx.coroutines.Job

open class BaseViewModel:ViewModel() {

    val jobs = ArrayList<Job>()
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

    override fun onCleared() {
        for (job in jobs){
            job.cancel()
            jobs.remove(job)
        }
        super.onCleared()
    }
}
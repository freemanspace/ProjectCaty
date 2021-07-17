package com.freeman.project.http

class MyApiData<T>(var msg:Int,var dataObj:T) {

    var isFinish = SingleLiveEvent<Boolean>()


    fun getisFinish(){
        isFinish.postValue(true)
    }

}
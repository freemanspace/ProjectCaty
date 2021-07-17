package com.freeman.project.http

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer

/**
 * 使用Coroutines(協程)呼叫api
 */
open class BaseApiManager {

    //
    @Synchronized
    fun <T>bindApiData(msg:Int,dataObj:T,onApiFinish:OnApiFinish<T>?){
        //通知異動
        onApiFinish?.onApiFinish(msg,dataObj)
    }
    //bind live data----------------------------------------------------------------------------------------------
    @Synchronized
    fun <T>bindMyApiData(lifecycleOwner: LifecycleOwner,msg:Int,dataObj:T,onApiFinish:OnApiFinish<T>?){
        val myApiData = MyApiData(msg, dataObj)
        //life data observer
        myApiData.isFinish.observe(lifecycleOwner, Observer {
            onApiFinish?.onApiFinish(myApiData.msg,myApiData.dataObj)
        })
        //通知異動
        myApiData.getisFinish()
    }

    /**
     * 再取不到lifecycleowner的地方(非activity,fragment)，可以使用
     */
    enum class ForeverStartLifecycleOwner : LifecycleOwner {
        INSTANCE;

        private val mLifecycleRegistry: LifecycleRegistry

        init {
            mLifecycleRegistry = LifecycleRegistry(this)
            mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
        }

        override fun getLifecycle(): Lifecycle {
            return mLifecycleRegistry
        }
    }

}
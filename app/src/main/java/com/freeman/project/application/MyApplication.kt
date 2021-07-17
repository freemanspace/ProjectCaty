package com.freeman.project.application

import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.freeman.project.sql.SqlManager


class MyApplication: MultiDexApplication() {

    companion object{
        lateinit var instance: MyApplication
            private set
    }

    override fun onCreate() {
        instance = this
        MultiDex.install(this)
        super.onCreate()

        //room init
        SqlManager.init(applicationContext)
    }

    override fun onTerminate() {
        super.onTerminate()
    }



}
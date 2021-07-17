package com.freeman.project.utils

import android.util.Log

object MyLog {

    @Synchronized
    fun err(tag: String?, msg: String?){
        if(tag==null){
            Log.e("test", msg!!)
        } else{
            Log.e(tag, msg!!)
        }
    }

    @Synchronized
    fun err(msg: String?) {
        //系統log
        Log.e("test", msg!!)
    }

    @Synchronized
    fun log(tag: String?, msg: String?){
        if(tag==null){
            Log.d("test", msg!!)
        } else{
            Log.d(tag, msg!!)
        }
    }

    @Synchronized
    fun log(msg: String?) {
        //系統log
        Log.d("test", msg!!)
    }
}
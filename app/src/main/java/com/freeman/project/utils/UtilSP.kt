package com.freeman.project.utils

import android.content.Context
import android.content.SharedPreferences
import com.freeman.project.application.MyApplication

/**
 * SharedPreferences
 */
object UtilSP {

    private val sharedPreferences:SharedPreferences by lazy {
        MyApplication.instance.getSharedPreferences("MySample",Context.MODE_PRIVATE)
    }
    private val AESIV = "freeman000000000"
    private val AESKEY = "freeman000000000freeman000000000"

    private fun setString(key:String,str:String){
        sharedPreferences.edit().putString(key,UtilEncrypt.encryptAes(AESIV, AESKEY,str)).apply()
    }

    private fun getString(key:String,def:String):String{
         val tmp = sharedPreferences.getString(key,def)
        return try{
            UtilEncrypt.decryptAES(AESIV, AESKEY,tmp!!)
        } catch (e:Exception){
            ""
        }
    }

    private fun setBoolean(key:String,boolean:Boolean){
        sharedPreferences.edit().putBoolean(key,boolean).apply()
    }

    private fun getBoolean(key:String,def:Boolean):Boolean{
        return sharedPreferences.getBoolean(key,def)
    }


    //-----------------------------------------data------------------------------------------------------------
    //device uuid
    fun getDevUUID():String{
        return getString("devuuid","")
    }
    fun setDevUUID(uuid:String){
        setString("devuuid",uuid)
    }
    //第一次使用
    fun getFirstTime():Boolean{
        return getBoolean("isFirstTime",true)
    }
    fun setFirstTime(){
        setBoolean("isFirstTime",false)
    }
    //是否紀錄身分證
    fun getIsSaveUserID():Boolean{
        return getBoolean("saveUID",true)
    }
    fun setIsSaveUserID(isSave:Boolean){
        setBoolean("saveUID",isSave)
    }
    //身分證
    fun getUserID():String{
        return getString("userID","")
    }
    fun setUserID(uid:String){
        setString("userID",uid)
    }

}
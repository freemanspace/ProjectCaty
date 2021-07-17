package com.freeman.project.utils

import com.freeman.project.utils.UtilDate.toString
import java.security.SecureRandom
import java.util.*

/**
 * SharedPreferences
 */
object UtilRandom {

    /**
     * 產生dev uuid 17+6 = 23
     */
    fun createRandomDevUUID():String{
        var time = Calendar.getInstance().toString(6)  //17碼
        return time+ randomString(6)
    }

    private const val RandomChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    private fun randomString(times:Int):String{
        val randomStr = StringBuilder()
        for (i in 0 until times){
            randomStr.append(RandomChars[SecureRandom().nextInt(61)])
        }
        return randomStr.toString()
    }


}
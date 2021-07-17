package com.freeman.project.utils

import com.freeman.project.utils.UtilDate.toString
import java.text.DecimalFormat
import java.util.*
import java.util.regex.Pattern


object UtilString {

    /**
     * 是否是email
     */
    fun String.isEmail():Boolean{
        return Pattern.matches("^.+@.+\\..+$", this)
    }

    /**
     * 是否是身分證
     */
    fun String.isPersonId():Boolean{
        return Pattern.matches("[a-zA-Z]\\d{9}", this)
    }

    /**
     * 是否是手機號碼
     */
    fun String.isMobile():Boolean{
        return Pattern.matches("[0][9][0-9]{8}", this)
    }

    /**
     * 數字文字前面補0
     */
    fun String.format0(size: Int):String{
        return String.format("%0${size}d", this.toInt())
    }

    fun String.toPrice():String{
        var df: DecimalFormat? = null
        df = if(this.indexOf(".")>0){
            DecimalFormat("###,##0.00")
        } else{
            DecimalFormat("###,##0")
        }
        return df.format(this.toDouble())
    }

    /**
     * 信用卡字串
     */
    fun String.toCreditCardStr():String{
        return if(this.length>=16){
            "${this.substring(0,4)}******${this.substring(10,16)}"
        } else {
            this
        }
    }

    /**
     * 銀行帳戶
     */
    fun String.toBankNoStr():String{
        return this
    }

    /**
     * 簡短銀行帳戶
     */
    fun String.toBankNoShortStr():String{
        return if(this.length>=4){
            "****${this.substring(this.length-4,this.length)}"
        } else {
            this
        }
    }

    /**
     * 身分證id字串
     */
    fun String.toIDStr():String{
        return if(this.length>=3){
            "${this.substring(0,this.length-3)}***"
        } else {
            this
        }
    }

    /**
     * 電話
     */
    fun String.toPhoneStr():String{
        return if(this.length>=6){
            "${this.substring(0,this.length-6)}***${this.substring(this.length-3,this.length)}"
        } else {
            this
        }
    }

    fun String.isToday():Boolean{
        return try{
            this == Calendar.getInstance().toString(2)
        } catch (e:Exception){
            false
        }
    }
}
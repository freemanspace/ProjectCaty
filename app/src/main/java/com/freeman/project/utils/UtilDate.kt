package com.freeman.project.utils

import android.annotation.SuppressLint
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

object UtilDate {


    /**
     * 文字轉換為calendar
     */
    @SuppressLint("SimpleDateFormat")
    fun String.toCalendar(type:Int):Calendar{
        var calendar: Calendar = Calendar.getInstance()
        if(this.isEmpty()){
            return calendar
        }
        val simpleDateFormat = when(type){
            0 -> SimpleDateFormat("yyyy/MM/dd")
            1 -> SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
            2 -> SimpleDateFormat("yyyyMMdd")
            3 -> SimpleDateFormat("yyyyMMddHHmmss")
            4 -> SimpleDateFormat("yyyy-MM-dd")
            5 -> SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            6 -> SimpleDateFormat("yyyyMMddHHmmssSSS")
            7 -> SimpleDateFormat("yyyy/MM")
            8 -> SimpleDateFormat("yyyyMMddHHmm")
            9 -> SimpleDateFormat("yyyy/MM/dd/ HH:mm")
            else -> SimpleDateFormat("yyyy/MM/dd")
        }
        try {
            calendar.time = simpleDateFormat.parse(this)
        } catch (e:Exception){
        }
        return calendar
    }

    /**
     * calendar轉換為文字
     */
    @SuppressLint("SimpleDateFormat")
    fun Calendar.toString(type:Int):String{
        val simpleDateFormat = when(type){
            0 -> SimpleDateFormat("yyyy/MM/dd")
            1 -> SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
            2 -> SimpleDateFormat("yyyyMMdd")
            3 -> SimpleDateFormat("yyyyMMddHHmmss")
            4 -> SimpleDateFormat("yyyy-MM-dd")
            5 -> SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            6 -> SimpleDateFormat("yyyyMMddHHmmssSSS")
            7 -> SimpleDateFormat("yyyy/MM")
            8 -> SimpleDateFormat("yyyyMMddHHmm")
            9 -> SimpleDateFormat("yyyy/MM/dd/ HH:mm")
            else -> SimpleDateFormat("yyyy/MM/dd")
        }
        try{
            return simpleDateFormat.format(this.time)
        } catch (e:Exception){
            return ""
        }
    }


    /**
     * 是否是同一天
     */
    fun isSameDay(calendar1: Calendar,calendar2: Calendar):Boolean{
        return calendar1.get(Calendar.YEAR)==calendar2.get(Calendar.YEAR) &&
                calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) &&
                calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH)
    }

    /**
     * 是否在區間內
     */
    fun between(start: Calendar,end:Calendar,date:Calendar):Boolean{
        return date.timeInMillis>=start.timeInMillis && date.timeInMillis<=end.timeInMillis
    }


    /**
     * 取得星期幾
     */
    fun getWeekDayString(calendar: Calendar):String{
        var weekdayInt = calendar.get(Calendar.DAY_OF_WEEK)
        if(calendar.firstDayOfWeek == Calendar.SUNDAY){
            weekdayInt = weekdayInt -1
            if(weekdayInt==0){
                weekdayInt = 7
            }
        }
        when(weekdayInt){
            1 -> return "ㄧ"
            2 -> return "二"
            3 -> return "三"
            4 -> return "四"
            5 -> return "五"
            6 -> return "六"
            7 -> return "日"
            else->return ""
        }
    }

    /**
     * 1 ->"ㄧ" 2 ->"二" 3 ->"三" 4 ->"四" 5 ->"五" 6 ->"六" 7 ->"日"
     */
    fun getWeekDay(calendar: Calendar):Int{
        var weekdayInt = calendar.get(Calendar.DAY_OF_WEEK)
        if(calendar.firstDayOfWeek == Calendar.SUNDAY){
            weekdayInt -= 1
            if(weekdayInt==0){
                weekdayInt = 7
            }
        }
        return weekdayInt
    }
}
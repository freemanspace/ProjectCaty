package com.freeman.project.utils

import java.math.BigDecimal
import java.text.DecimalFormat

object UtilNumber {

    /**
     * double四捨五入
     */
    fun Double.getDouble(scale:Int):Double{
        return BigDecimal(this).setScale(scale,BigDecimal.ROUND_HALF_UP).toDouble()
    }

    /**
     * float四捨五入
     */
    fun Double.getFloat(scale:Int): Float {
        return BigDecimal(this).setScale(scale,BigDecimal.ROUND_HALF_UP).toFloat()
    }

    /**
     * 前面補0
     */
    fun Int.format0(size:Int):String{
        return String.format("%0${size}d",this)
    }

    /**
     * 加千分位
     */
    fun Int.toPrice():String{
        return DecimalFormat("###,##0").format(this)
    }

    /**
     * 加千分位
     */
    fun Double.toPrice():String{
        return DecimalFormat("###,##0.00").format(this)
    }

}
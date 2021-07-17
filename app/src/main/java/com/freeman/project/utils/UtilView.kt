package com.freeman.project.utils

import android.os.SystemClock
import android.view.View
import com.freeman.project.R

object UtilView {

    /**
     * 200毫秒內不重複觸發
     */
    fun View.onSingleClick(listener:(View)->Unit){
        setOnClickListener{
            val time = this.getTag(R.id.click_time) as? Long ?:0
            if(this.isEnabled && this.isClickable && SystemClock.uptimeMillis() - time>=200){
                this.setTag(R.id.click_time,SystemClock.uptimeMillis())
                listener.invoke(this)
            }
        }
    }


}
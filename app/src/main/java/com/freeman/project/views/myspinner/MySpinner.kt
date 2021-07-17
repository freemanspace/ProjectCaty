package com.freeman.project.views.myspinner

import android.content.Context
import android.util.AttributeSet
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatSpinner

class MySpinner: AppCompatSpinner {

    constructor(context: Context):super(context){
        initView(context)
    }

    constructor(context: Context,attrs: AttributeSet?):super(context,attrs){
        initView(context)
    }

    private fun initView(context: Context){
    }

    /**
     * 指定資料
     */
    fun <T>setDatas(datas:List<T>){
        this.apply {
            adapter = MySpinnerStdAdapter(context,datas)
        }
    }

    /**
     * 指定adapter，客制任何ui
     */
    fun <T>setDatas(myAdapter:ArrayAdapter<T>){
        this.apply {
            adapter = myAdapter
        }
    }
}
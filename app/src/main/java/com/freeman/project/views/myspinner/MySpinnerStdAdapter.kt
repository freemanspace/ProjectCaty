package com.freeman.project.views.myspinner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.freeman.project.R
import com.freeman.project.databinding.AdapterMyspinnerDropdownBinding
import com.freeman.project.databinding.AdapterMyspinnerStdBinding

/**
 * 標準myspinner
 */
class MySpinnerStdAdapter<T> : ArrayAdapter<T> {
    var _datas : List<T>

    constructor(context:Context,objects:List<T>):super(context,
        R.layout.adapter_myspinner_std,objects){
        _datas = objects
    }

    //原始畫面
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var viewBinding = AdapterMyspinnerStdBinding.inflate(LayoutInflater.from(context),parent,false)
        viewBinding.apply {
            tvItem.text = _datas[position].toString()
        }
        return viewBinding.root
    }

    //下拉畫面
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var viewBinding = AdapterMyspinnerDropdownBinding.inflate(LayoutInflater.from(context),parent,false)
        viewBinding.apply {
            tvItem.text = _datas[position].toString()
        }
        return viewBinding.root
    }


}
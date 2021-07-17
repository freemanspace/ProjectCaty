package com.freeman.project.views.myspinner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.freeman.project.R
import com.freeman.project.databinding.AdapterMyspinnerDropdownBinding
import com.freeman.project.databinding.AdapterMyspinnerRadius10Binding

/**
 * 客制spinner sample
 */
class MySpinnerRadius10Adapter<T> : ArrayAdapter<T> {
    var _datas : List<T>

    constructor(context:Context,objects:List<T>):super(context,
        R.layout.adapter_myspinner_radius10,objects){
        _datas = objects
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var viewBinding = AdapterMyspinnerRadius10Binding.inflate(LayoutInflater.from(context),parent,false)
        viewBinding.apply {
            tvItem.text = _datas[position].toString()
        }
        return viewBinding.root
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var viewBinding = AdapterMyspinnerDropdownBinding.inflate(LayoutInflater.from(context),parent,false)
        viewBinding.apply {
            tvItem.text = _datas[position].toString()
        }
        return viewBinding.root
    }


}
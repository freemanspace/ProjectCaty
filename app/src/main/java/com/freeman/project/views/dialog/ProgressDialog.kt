package com.freeman.project.views.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import com.freeman.project.R
import com.freeman.project.databinding.DialogProgressBinding


class ProgressDialog(context:Context) : Dialog(context, R.style.dialogNobackground) {

    lateinit var viewBinding: DialogProgressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        //view
        viewBinding = DialogProgressBinding.inflate(LayoutInflater.from(context))
        setContentView(viewBinding.root)

        //
        val lp = window?.attributes as WindowManager.LayoutParams
        lp.x = 0
        lp.y = 0
        onWindowAttributesChanged(lp)
    }

}
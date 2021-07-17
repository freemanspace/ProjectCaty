package com.freeman.project.views.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import com.freeman.project.R
import com.freeman.project.databinding.DialogMsgBinding
import com.freeman.project.utils.AppSetting
import com.freeman.project.utils.UtilView.onSingleClick
import com.freeman.project.views.OnAction


class MsgDialog(context:Context,private var title:String?="",private var msg:String?,private var strOk:String="確定",private var onAction: OnAction?=null,private var obj:Any?=null) : Dialog(context, R.style.dialogNobackground) {

    companion object {
        val Action_OK = 0
    }

    lateinit var viewBinding: DialogMsgBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        //view
        viewBinding = DialogMsgBinding.inflate(LayoutInflater.from(context))
        setContentView(viewBinding.root)

        viewBinding.apply {
            if(title==null || title!!.isEmpty()){
                tvTitle.visibility = View.GONE
            }
            tvTitle.setText(title)
            tvMsg.setText(msg)
            tvMsg.movementMethod = ScrollingMovementMethod.getInstance()
            btnOK.setText(strOk)
            btnOK.onSingleClick {
                onAction?.onAction(Action_OK,obj)
                dismiss()
            }
        }

        //
        val lp = window?.attributes as WindowManager.LayoutParams
        lp.x = 0
        lp.y = 0
        lp.width = (AppSetting.DeviceWidth*0.7).toInt()
        //lp.height = (AppSetting.DeviceHeight*0.5).toInt()
        onWindowAttributesChanged(lp)
    }
}
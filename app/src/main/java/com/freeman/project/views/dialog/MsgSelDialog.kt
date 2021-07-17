package com.freeman.project.views.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import com.freeman.project.R
import com.freeman.project.databinding.DialogMsgselectBinding
import com.freeman.project.utils.AppSetting
import com.freeman.project.utils.UtilView.onSingleClick
import com.freeman.project.views.OnAction


class MsgSelDialog(
    context: Context,
    private var title: String?="",
    private var msg: String?,
    private var str_no: String?="取消",
    private var str_ok: String?="確定",
    private var onAction: OnAction?=null,
    private var obj: Any?=null
) : Dialog(context, R.style.dialogNobackground) {

    companion object {
        val Action_OK = 0
        val Action_NO = 1
    }

    lateinit var dialogMsgselectBinding: DialogMsgselectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        //view
        dialogMsgselectBinding = DialogMsgselectBinding.inflate(LayoutInflater.from(context))
        setContentView(dialogMsgselectBinding.root)

        dialogMsgselectBinding.apply {
            if(title==null || title!!.isEmpty()){
                tvTitle.visibility = View.GONE
            }
            tvTitle.text = title
            tvMsg.text = msg
            tvMsg.movementMethod = ScrollingMovementMethod.getInstance()
            btnNO.paint.flags = Paint.UNDERLINE_TEXT_FLAG
            btnNO.text = "$str_no"
            btnNO.onSingleClick {
                onAction?.onAction(Action_NO,obj)
                dismiss()
            }
            btnOK.text = "$str_ok"
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
        onWindowAttributesChanged(lp)
    }
}
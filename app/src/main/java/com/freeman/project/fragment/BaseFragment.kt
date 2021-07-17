package com.freeman.project.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.freeman.project.R
import com.freeman.project.activity.MainActivity
import com.freeman.project.databinding.ViewHeaderStdBinding
import com.freeman.project.utils.UtilView.onSingleClick
import com.freeman.project.views.OnAction
import com.freeman.project.views.dialog.MsgSelDialog

abstract class BaseFragment<VB:ViewBinding,VM:BaseViewModel> : Fragment(),BackHandlerHelper.onFragmentBack {

    protected lateinit var viewBinding:VB
    protected lateinit var viewModel:VM
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(this).get(createViewModelClass())
        viewBinding = createViewBinding(container)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStatusBarColor(R.color.black)

        //loading
        viewModel.loadingLiveData.observe(
            this,
            Observer {
                if(it==true){
                    showLoading()
                } else if(it==false){
                    closeLoading()
                }
            }
        )
        //api error msg
        viewModel.apiErrMsg.observe(
            this,
            {
                showMsg(it,null)
            }
        )
        initView()
    }

    override fun onResume() {
        super.onResume()
        if(MainActivity.instance.getTopFragment()==this){   //如果onresume時在前景
            onShow()
        }
    }

    override fun onPause() {
        super.onPause()
        if(MainActivity.instance.getTopFragment()==this){  //如果onpause時在前景
            unShow()
        }
    }

    //--------------------------------------------------------------------
    override fun onBackPress(): Boolean {
        finish()
        return true
    }

    abstract fun createViewBinding(container: ViewGroup?): VB
    abstract fun createViewModelClass():Class<VM>

    abstract fun initView()

    protected open fun onShow() {  //顯示畫面 取代onresume，因為resume不管是否在前景都會動作
    }
    protected open fun unShow(){    //取代onpause
    }
    /**
     * 為了讓其他人可以呼叫onShow所以開此function
     */
    open fun callOnShow(){
        onShow()
    }
    /**
     * 為了讓其他人可以呼叫unShow所以開此function
     */
    open fun callUnShow(){
        unShow()
    }

    /**
     * 基本header init
     */
    protected open fun initHeader(viewBinding: ViewHeaderStdBinding,title:String){
        viewBinding.apply {
            btnTitleLeft.onSingleClick {
                finish()
            }
            tvTitle.text = title
        }
    }

    //--------------------------------------------------------------------
    open fun finish(){
        MainActivity.instance.removeFragment(this,true)
    }

    open fun removeFragment(fragment:Fragment,needShow:Boolean){
        MainActivity.instance.removeFragment(fragment,needShow)
    }

    open fun removeAllFragment(){
        MainActivity.instance.removeAllFragment()
    }

    /**
     * 螢幕不關
     */
    fun setScreenOn(){
        MainActivity.instance.setScreenOn()
    }

    /**
     * 更改status bar color
     */
    protected fun setStatusBarColor(colorRes:Int){
        MainActivity.instance.setStatusBarColor(colorRes)
    }

    /**
     * 顯示toast
     * @param msg
     */
    fun showToast(msg: String){
        MainActivity.instance.showToast(msg)
    }

    /**
     * 顯示loading
     */
    fun showLoading(){
        MainActivity.instance.showLoading()
    }

    /**
     * 關閉loading
     */
    fun closeLoading(){
        MainActivity.instance.closeLoading()
    }

    /**
     * 是否正在顯示loading
     */
    fun isLoading():Boolean {
        return MainActivity.instance.isLoading()
    }

    /**
     * 顯示離開dialog
     */
    fun showExitDialog(){
        showMsgSel("是否離開APP", object : OnAction {
            override fun onAction(actionID: Int, data: Any?) {
                if(actionID==MsgSelDialog.Action_OK){
                    MainActivity.instance.exitApp()
                }
            }
        })
    }


    /**
     * 顯示msg dialog
     * @param msg
     * @param onAction
     */
    open fun showMsg(msg: String?, onAction: OnAction?) {
        MainActivity.instance.showMsg(msg = msg,onAction = onAction)
    }

    open fun showMsg(title: String?, msg: String?, onAction: OnAction?) {
        MainActivity.instance.showMsgSel(title,msg,onAction)
    }

    /**
     * 顯示msg select dialog
     * @param msg
     * @param onAction
     */
    open fun showMsgSel(msg: String?, onAction: OnAction?) {
        MainActivity.instance.showMsgSel(msg=msg,onAction = onAction)
    }

    open fun showMsgSel(title: String?="",msg: String?, onAction: OnAction?) {
        MainActivity.instance.showMsgSel(title = title,msg=msg,onAction = onAction)
    }

}


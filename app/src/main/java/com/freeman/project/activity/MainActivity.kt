package com.freeman.project.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.freeman.project.R
import com.freeman.project.databinding.ActivityMainBinding
import com.freeman.project.fragment.BackHandlerHelper
import com.freeman.project.fragment.BaseFragment
import com.freeman.project.fragment.zoomlist.ZoomListFragment
import com.freeman.project.fragment.start.StartFragment
import com.freeman.project.views.OnAction
import com.freeman.project.views.dialog.MsgDialog
import com.freeman.project.views.dialog.MsgSelDialog
import com.freeman.project.views.dialog.ProgressDialog
import kotlin.system.exitProcess

open class MainActivity : AppCompatActivity() ,Handler.Callback{

    companion object{
        lateinit var instance: MainActivity
            private set
    }

    lateinit var viewBinding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this

        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        //下方bar color
        window.navigationBarColor = Color.BLACK

        //啟動start fragment
        replaceFragment(StartFragment.newInstance())
    }

    //-----------------------back 處理--------------------------------------
    /**
     * 偵查back press，如果fragment沒有處理，就統一由activity處理
     */
    override fun onBackPressed() {
        //處理fragment back
        if(!BackHandlerHelper.handleBackPress(this)){
            //popup fragment
            supportFragmentManager.popBackStackImmediate()
            if(!checkFragmentOnShow()){
                finish()
            }
        }
    }

    //檢核前景的fragment並呼叫onshow
    private fun checkFragmentOnShow():Boolean{
        val lastFragment = BackHandlerHelper.getLastVisibleFragment(supportFragmentManager)

        if((lastFragment is BaseFragment<*,*>)){
            lastFragment.callOnShow()
            return true
        }
        return false
    }
    //-----------------------back 處理--------------------------------------

    //-----------------------fragment處理------------------------------------
    /**
     * 取得最上層的fragment
     */
    open fun getTopFragment():BaseFragment<*,*>?{
        val fragments: List<Fragment> = supportFragmentManager.fragments
        fragments.asReversed().forEach {
            if(it is BaseFragment<*,*>){
                return it
            }
        }
        return null
    }

    /**
     * 取得全部fragment
     */
    open fun getAllFragments():List<Fragment>{
        return supportFragmentManager.fragments
    }

    /**
     * 新加fragment
     */
    open fun addFragment(fragment: Fragment){
        getTopFragment()?.callUnShow()  //先將前景fragment停止
        supportFragmentManager.beginTransaction().let {
            it.add(R.id.fragmentLayout, fragment)
            //it.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            it.commitNow()
        }
    }

    /**
     * 取代fragment
     */
    open fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().let{
            it.replace(R.id.fragmentLayout, fragment)
            //it.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            it.commitNow()
        }
    }

    /**
     * 移除fragment，
     * 如果一次移除多頁，只能在最後一頁給needShow=ture，
     * 因為只有最後一頁需要onshow
     */
    open fun removeFragment(fragment: Fragment,needShow:Boolean){
        supportFragmentManager.beginTransaction().let {
            it.remove(fragment)
            //it.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            it.commitNow()
        }
        supportFragmentManager.popBackStackImmediate()

        if(needShow) {  //如果都沒有fragment，加入homefragment
            if(!checkFragmentOnShow()){
                addFragment(ZoomListFragment.newInstance())
            }
        }
    }

    /**
     * 移除全部fragment
     */
    open fun removeAllFragment(){
        //如果tag为null，flags为0时，弹出回退栈中最上层的那个fragment。
        //如果tag为null ，flags为1时，弹出回退栈中所有fragment。
        //如果tag不为null，那就会找到这个tag所对应的fragment，flags为0时，弹出该
        //fragment以上的Fragment，如果是1，弹出该fragment（包括该fragment）以
        //上的fragment。
        //supportFragmentManager.popBackStack(1,0)
        val fragments: List<Fragment> = supportFragmentManager.fragments
        for (fragment in fragments){
            removeFragment(fragment,false)
        }
    }
    //-----------------------fragment處理------------------------------------

    //-----------------------app是否有在操作-------------------------------------------
    //用來偵測是否有操作APP
    private val appTimeOut:Long = 10*60*1000
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        handler.removeMessages(MSG_TimeOut)
        handler.sendEmptyMessageDelayed(MSG_TimeOut,appTimeOut) //10分鐘
        return super.dispatchTouchEvent(ev)
    }

    override fun onDestroy() {
        handler.removeMessages(MSG_TimeOut)
        super.onDestroy()
    }

    //用來偵測是否遇時操作
    private val MSG_TimeOut = 0
    private val handler = Handler(Looper.getMainLooper(), this)
    override fun handleMessage(msg: Message): Boolean {
        when(msg.what){
            MSG_TimeOut->{
                /*
                if(MyApplication.instance.isLogin()){
                    MyApplication.instance.logout()
                    showMsg("您已超過10分鐘未操作APP，將登出保護資料安全",object:OnDialogClickListener{
                        override fun onDialogClick(actionID: Int, obj: Any?) {
                            UtilRoute.routeByBottomNav(this@BaseActivity,UtilRoute.BottomNav.Home,false,true)
                        }
                    })
                }*/
            }
        }
        return true
    }
    //-----------------------app是否有在操作-------------------------------------------
    /**
     * 螢幕不關
     */
    fun setScreenOn(){
        window.setFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        )
    }

    /**
     * 更改status bar color
     */
    fun setStatusBarColor(colorRes:Int){
        window.statusBarColor = getColor(colorRes)
    }

    /**
     * 顯示toast
     * @param msg
     */
    fun showToast(msg: String){
        try{
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        } catch (e: Exception){
        }
    }

    var progressDialog:ProgressDialog? = null
    /**
     * 顯示loading
     */
    fun showLoading(){
        if(progressDialog==null || progressDialog?.isShowing==false){
            progressDialog = ProgressDialog(this)
            progressDialog?.show()
        }
    }

    /**
     * 關閉loading
     */
    fun closeLoading(){
        if(progressDialog?.isShowing==true){
            progressDialog?.dismiss()
        }
        progressDialog = null
    }

    /**
     * 是否正在顯示loading
     */
    fun isLoading():Boolean {
        return progressDialog?.isShowing==true
    }

    /**
     * 顯示msg dialog
     */
    open fun showMsg(title:String?="",msg: String?, onAction: OnAction?) {
        MsgDialog(this,title = title,msg = msg,onAction = onAction).show()
    }

    /**
     * 顯示msg select dialog
     *
     */
    open fun showMsgSel(title: String?="",msg: String?, onAction: OnAction?) {
        MsgSelDialog(this,title = title, msg = msg, onAction = onAction).show()
    }

    /**
     * 離開APP
     */
    fun exitApp(){
        removeAllFragment()
        finish()
        exitProcess(0)
    }
}
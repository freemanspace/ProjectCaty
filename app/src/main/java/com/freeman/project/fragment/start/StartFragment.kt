package com.freeman.project.fragment.start

import android.view.ViewGroup
import com.freeman.project.activity.MainActivity
import com.freeman.project.databinding.FragmentStartBinding
import com.freeman.project.fragment.BaseFragment
import com.freeman.project.fragment.zoomlist.ZoomListFragment
import com.freeman.project.utils.AppSetting
import com.freeman.project.utils.UtilRoute
import com.freeman.project.views.OnAction
import kotlinx.coroutines.*

/**
 * 起始頁
 */
class StartFragment : BaseFragment<FragmentStartBinding,StartFragmentModel>() {

    companion object{
        fun newInstance(): StartFragment {
            return StartFragment()
        }
    }

    override fun createViewBinding(container: ViewGroup?): FragmentStartBinding {
        return FragmentStartBinding.inflate(layoutInflater,container,false)
    }

    override fun createViewModelClass(): Class<StartFragmentModel> {
        return StartFragmentModel::class.java
    }

    override fun initView(){
        AppSetting.init(requireActivity())

        viewModel.stateEvent.observe(viewLifecycleOwner,
            {
                when(it){
                    1->{    //root
                        showMsg("偵測到裝置可能進行ROOT，為了你的交易安全，請謹慎使用，以避免資訊外洩風險。",
                            object : OnAction {
                                override fun onAction(actionID: Int, data: Any?) {
                                    viewModel.startCheckState(requireContext(),2)
                                }
                            })
                    }
                    2->{    //checkEmulator
                        showMsg("偵測到裝置可能在模擬器上運行，為了你的交易安全，請謹慎使用，以避免資訊外洩風險。",
                            object :OnAction{
                                override fun onAction(actionID: Int, data: Any?) {
                                    viewModel.startCheckState(requireContext(),3)
                                }
                            })
                    }
                    3->{    //adb
                        showMsg("你已啟動ADB",object :OnAction{
                            override fun onAction(actionID: Int, data: Any?) {
                                viewModel.startCheckState(requireContext(),4)
                            }
                        })
                    }
                    4->{
                        showMsg("請勿在Google Play商店以外地方下載本ＡＰＰ", object : OnAction {
                            override fun onAction(actionID: Int, data: Any?) {
                                viewModel.startCheckState(requireContext(),5)
                            }
                        })
                    }
                    5->{
                        //清理cache
                    }
                    6->{
                        //完成檢核
                        finishStart()
                    }
                }
            })

        viewModel.startCheckState(requireContext(),1)
    }

    fun finishStart(){
        MainScope().launch {
            //delay(300)
            UtilRoute.routeNewFragment(ZoomListFragment.newInstance())
            finish()
        }
    }


    override fun onBackPress(): Boolean {
        showExitDialog()
        return true
    }
}
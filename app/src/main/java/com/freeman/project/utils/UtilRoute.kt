package com.freeman.project.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import com.freeman.project.activity.MainActivity
import com.freeman.project.fragment.BaseFragment

object UtilRoute {

    /**
     * 新fragment
     */
    fun routeNewFragment(fragment: Fragment){
        MainActivity.instance.addFragment(fragment)
    }

    /**
     * 會透過FragmentResultListener call back
     */
    fun routeNewFragmentCallBack(fragment: Fragment,targetFragment:Fragment,requestKey:String,fragmentResultListener: FragmentResultListener){
        MainActivity.instance.supportFragmentManager.setFragmentResultListener(
            requestKey,
            targetFragment.viewLifecycleOwner,
            fragmentResultListener)
        MainActivity.instance.addFragment(fragment)
    }

    /**
     * fragment回傳資料
     */
    fun finishCallBack(fragment:BaseFragment<*,*>,requestKey:String,bundle:Bundle){
        MainActivity.instance.supportFragmentManager.setFragmentResult(
            requestKey,
            bundle
        )
        fragment.finish()
    }

}
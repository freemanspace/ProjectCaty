package com.freeman.project.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager


object BackHandlerHelper {

    /**
     * 将back事件分发给 FragmentManager 中管理的子Fragment，如果该 FragmentManager 中的所有Fragment都
     * 没有处理back事件，则尝试 FragmentManager.popBackStack()
     * @return 如果处理了back键则返回 **true**
     * @see .handleBackPress
     * @see .handleBackPress
     */
    @Synchronized
    fun handleBackPress(fragmentManager: FragmentManager): Boolean {
        val fragments: List<Fragment> = fragmentManager.fragments
        for (i in fragments.indices.reversed()) {
            val child: Fragment = fragments[i]
            if (isFragmentBackHandled(child)) {
                return true
            }
        }
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack()
            return true
        }
        return false
    }

    fun handleBackPress(fragment: Fragment?):Boolean{
        if (isFragmentBackHandled(fragment)) {
            return true
        }
        return false
    }

    /**
     * Activity判斷目前的fragment是否有處理back event
     */
    @Synchronized
    fun handleBackPress(fragmentActivity: FragmentActivity): Boolean {
        var fragment = getLastVisibleFragment(fragmentActivity.supportFragmentManager)
        return handleBackPress(fragment)
        //return handleBackPress(fragmentActivity.supportFragmentManager)
    }

    /**
     * 因為glide會在外面加入一個不可視的fragment所以使用此方法取得
     */
    fun getLastVisibleFragment(fragmentManager: FragmentManager):Fragment?{
        val fragments: List<Fragment> = fragmentManager.fragments
        fragments.asReversed().forEach {
            if(it is BaseFragment<*,*>){
                return it
            }
        }
        return null
    }

    /**
     * 判断Fragment是否处理了Back键
     * @return 如果处理了back键则返回 **true**
     */
    @Synchronized
    fun isFragmentBackHandled(fragment: Fragment?): Boolean {
        return (fragment != null //&& fragment?.isVisible()
                //&& fragment.getUserVisibleHint() //for ViewPager
                && fragment is onFragmentBack
                && (fragment as onFragmentBack).onBackPress())
    }


    /**
     * fragment back press interface
     */
    interface onFragmentBack {
        fun onBackPress():Boolean
    }
}
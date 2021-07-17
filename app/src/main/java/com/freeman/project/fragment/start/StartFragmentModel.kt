package com.freeman.project.fragment.start

import android.content.Context
import android.provider.Settings
import com.freeman.project.BuildConfig
import com.freeman.project.fragment.BaseViewModel
import com.freeman.project.http.SingleLiveEvent
import com.freeman.project.sql.SqlManager
import com.freeman.project.sql.table.CacheVersion
import com.freeman.project.utils.UtilPhoneSecurity
import com.freeman.project.utils.UtilString.isToday
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class StartFragmentModel : BaseViewModel() {

    /**
     *  1->check root
     *  2->check emulator
     *  3->check adb
     *  4->check install
     *  5->clear cache
     *  6->finish
     */
    val stateEvent = SingleLiveEvent<Int>()

    fun startCheckState(context: Context,state:Int){
        when(state){
            1->{
                checkRoot(context)
            }
            2->{
                checkEmulator(context)
            }
            3->{
                checkADB(context)
            }
            4->{
                checkInstall(context)
            }
            5->{
                clearCache()
            }
        }
    }

    //state 1 check root
    private fun checkRoot(context: Context){
        if(UtilPhoneSecurity.checkRoot()){
            stateEvent.postValue(1)
        } else{
            checkEmulator(context)
        }
    }

    //state 2 檢查是否是模擬器
    private fun checkEmulator(context: Context){
        if(UtilPhoneSecurity.checkEmulator()){
            stateEvent.postValue(2)
        } else{
            checkADB(context)
        }
    }

    //state 3 check adb
    private fun checkADB(context: Context){
        val adb = Settings.Secure.getInt(context.contentResolver, Settings.Global.ADB_ENABLED,0)
        if(adb==1){
            stateEvent.postValue(3)
        } else{
            checkInstall(context)
        }
    }

    //state 4 check install
    private fun checkInstall(context: Context){
        if(BuildConfig.IsTest) {
            clearCache()
        } else{
            var installPackage = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                val installInfo = context.packageManager.getInstallSourceInfo(BuildConfig.APPLICATION_ID)
                installInfo.installingPackageName
            } else {
                context.packageManager.getInstallerPackageName(
                    BuildConfig.APPLICATION_ID)
            }

            if (installPackage?.startsWith("com.android.vending") == true) {
                clearCache()
            } else {
                stateEvent.postValue(4)
            }
        }
    }

    private fun clearCache(){
        MainScope().launch {
            var deferred = async(Dispatchers.IO) {
                var zoomInfoCache = SqlManager.dataBase.cacheVersionDao().let {
                    it.getZoomInfo(CacheVersion.zoomInfo)
                }
                if(zoomInfoCache?.version?.isToday()==true){
                    //do not thing
                } else{
                    SqlManager.dataBase.zoomInfoDao().apply {
                        clearZoomInfo()
                    }
                }
            }
            deferred.await()
            stateEvent.postValue(6)
        }
    }
}
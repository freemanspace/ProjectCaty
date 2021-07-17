package com.freeman.project.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import com.freeman.project.BuildConfig
import java.io.File

object UtilShare {

    /**
     * 分享文字
     */
    @Synchronized
    fun shareMsg(context:Context,title:String,msg:String){
        Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, title)
            putExtra(Intent.EXTRA_TEXT, msg)
            context.startActivity(Intent.createChooser(this, "Share"))
        }
    }

    /**
     * 分享圖片
     */
    @Synchronized
    fun shareImg(context:Context,title:String,msg:String,filePath:String?) {
        Intent(Intent.ACTION_SEND).apply {
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            type = "image/*"
            putExtra(Intent.EXTRA_STREAM, getProviderUri(context,filePath))
            putExtra(Intent.EXTRA_SUBJECT, title)
            putExtra(Intent.EXTRA_TEXT, msg)
            context.startActivity(Intent.createChooser(this, "Share"))
        }
    }

    /**
     * 使用FileProvider取得分享檔案的位置
     */
    @Synchronized
    fun getProviderUri(context: Context,filePath: String?):Uri{
        val file = File(filePath)
        return if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){   //6.0 23
            FileProvider.getUriForFile(context,BuildConfig.APPLICATION_ID+".file_provider",file)
        } else{
            Uri.fromFile(file)
        }
    }

}
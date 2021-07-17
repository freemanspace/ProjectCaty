package com.freeman.project.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat

object UtilFile {

    /**
     * 儲存外部空間檔案，DCIM位置
     **/
    @Synchronized
    fun saveToDCIM(context: Context,bitmap: Bitmap):Uri{
        val fileFolder = "App"
        val imageTime = System.currentTimeMillis()
        val imageTimeSec = imageTime / 1000
        val fileName = "${SimpleDateFormat("yyyyMMddhhmmss").format(imageTime)}.png"

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
            val values = ContentValues().apply {
                put(MediaStore.MediaColumns.TITLE, fileName)
                put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/$fileFolder")
                put(MediaStore.MediaColumns.DATE_TAKEN, imageTime)
                put(MediaStore.MediaColumns.WIDTH, bitmap.width)
                put(MediaStore.MediaColumns.HEIGHT, bitmap.height)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
                put(MediaStore.MediaColumns.IS_PENDING, true)
            }
            val resolver = context.contentResolver
            val collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            val uri = resolver.insert(collection, values) ?: return Uri.parse("")
            resolver.openFileDescriptor(uri, "w", null).use {
                if (it != null) {
                    val fileOutputStream = FileOutputStream(it.fileDescriptor)
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream)
                    fileOutputStream.flush()
                    fileOutputStream.close()
                }
            }
            values.clear()
            values.put(MediaStore.Images.Media.IS_PENDING, false)
            resolver.update(uri, values, null, null)
            return uri
        } else{
            val filePath = "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)}/${File.separator}/$fileFolder/$fileName"
            createFile(filePath)

            val values = ContentValues().apply {
                put(MediaStore.MediaColumns.DATA, filePath)
                put(MediaStore.MediaColumns.TITLE, fileName)
                put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                put(MediaStore.MediaColumns.DATE_ADDED, imageTimeSec)
                put(MediaStore.MediaColumns.DATE_MODIFIED, imageTimeSec)
                put(MediaStore.MediaColumns.WIDTH, bitmap.width)
                put(MediaStore.MediaColumns.HEIGHT, bitmap.height)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
            }
            val resolver = context.contentResolver
            val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values) ?: return Uri.parse("")
            resolver.openFileDescriptor(uri, "w", null).use {
                if (it != null) {
                    val fileOutputStream = FileOutputStream(it.fileDescriptor)
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream)
                    fileOutputStream.flush()
                    fileOutputStream.close()
                }
            }
            resolver.update(uri, values, null, null)
            return uri
        }
    }


    /**
     * 生成目錄及檔案
     */
    @Synchronized
    private fun createFile(filePath:String): File{
        val file = File(filePath)
        try {
            if (!file.exists()) {
                file.parentFile?.mkdirs()
                file.createNewFile()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return file
    }



}
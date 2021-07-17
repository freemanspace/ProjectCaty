package com.sinopac.view.AsyncImgView

import android.graphics.*
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

class RoundTransform(var radius: Float,var borderWidth: Int,var color: Int) : BitmapTransformation() {


    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap? {
        return roundCrop(pool, toTransform)
    }

    /**
     * 切倒角
     */
    private fun roundCrop(pool: BitmapPool, source: Bitmap?): Bitmap? {
        if (source == null) return null
        var result: Bitmap? = pool[source.width, source.height, Bitmap.Config.ARGB_8888]
        if (result == null) {
            result = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
        }
        var paint = Paint()
        paint.isAntiAlias = true
        val rectF = RectF(0f, 0f, source.width.toFloat(), source.height.toFloat())
        val canvas = Canvas(result!!)

        /*
        paint.setStyle(Paint.Style.STROKE);//设置填充样式为描边
        paint.setColor(Color.WHITE);//设置颜色为黑色
        paint.setStrokeWidth(2);//设置笔触宽度为2像素
        canvas.drawRoundRect(rectF, radius, radius, paint);//绘制一个描边的圆角矩形

        paint.setStyle(Paint.Style.FILL);//设置填充样式为填充
        BitmapShader bs=new BitmapShader(result,Shader.TileMode.REPEAT,Shader.TileMode.MIRROR);
        paint.setShader(bs);//设置渲染对象
        canvas.drawRoundRect(rectF, radius, radius, paint);
        */

        //切圓角
        paint.shader =
            BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        canvas.drawRoundRect(rectF, radius, radius, paint)
        //劃邊筐
        paint = Paint()
        paint.isAntiAlias = true
        paint.style = Paint.Style.STROKE //设置填充样式为描边
        paint.color = color //设置颜色为黑色
        paint.setStrokeWidth(borderWidth.toFloat()) //设置笔触宽度为2像素
        canvas.drawRoundRect(rectF, radius, radius, paint) //绘制一个描边的圆角矩形
        return result
    }


    override fun updateDiskCacheKey(messageDigest: MessageDigest) {}
}
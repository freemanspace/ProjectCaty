package com.sinopac.view.AsyncImgView

import android.content.res.Resources
import android.graphics.*
import android.os.Build
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

class CycleTransform(var borderWidth: Int, var color: Int) : BitmapTransformation() {

    companion object{
        private val PAINT_FLAGS = Paint.DITHER_FLAG or Paint.FILTER_BITMAP_FLAG
        private val CIRCLE_CROP_PAINT_FLAGS: Int = PAINT_FLAGS or Paint.ANTI_ALIAS_FLAG
        private val CIRCLE_CROP_SHAPE_PAINT: Paint = Paint(CIRCLE_CROP_PAINT_FLAGS)
        private val CIRCLE_CROP_BITMAP_PAINT: Paint = Paint(CIRCLE_CROP_PAINT_FLAGS)
        private val CIRCLE_CROP_BORDER_PAINT = Paint()
    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap? {
        borderWidth = (borderWidth*Resources.getSystem().displayMetrics.density).toInt()
        //设置画笔为取交集模式
        CIRCLE_CROP_BITMAP_PAINT.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN))
        CIRCLE_CROP_BORDER_PAINT.setAntiAlias(true) //去毛邊
        CIRCLE_CROP_BORDER_PAINT.setStyle(Paint.Style.STROKE) //空心效果

        return roundCrop(pool, toTransform)
    }

    /**
     * 切園
     */
    private fun roundCrop(pool: BitmapPool, source: Bitmap?): Bitmap? {
        if (source == null) return null
        val srcWidth = source.width
        val srcHeight = source.height
        val destMinEdge: Int = Math.min(srcWidth, srcHeight)
        val radius = destMinEdge / 2f

        val scaleX = destMinEdge / srcWidth.toFloat()
        val scaleY = destMinEdge / srcHeight.toFloat()
        val maxScale = Math.max(scaleX, scaleY)

        val scaledWidth = maxScale * srcWidth
        val scaledHeight = maxScale * srcHeight
        val left = (destMinEdge - scaledWidth) / 2f
        val top = (destMinEdge - scaledHeight) / 2f

        val destRect = RectF(left, top, left + scaledWidth, top + scaledHeight)

        //Alpha is required for this transformation.

        // Alpha is required for this transformation.
        val toTransform: Bitmap = getAlphaSafeBitmap(pool, source)!!
        val outConfig: Bitmap.Config =getAlphaSafeConfig(source)!!

        var result: Bitmap? = pool[destMinEdge, destMinEdge, outConfig]
        if (result == null) {
            result = Bitmap.createBitmap(destMinEdge, destMinEdge, Bitmap.Config.ARGB_8888)
        }
        result!!.setHasAlpha(true)

        try{
            val canvas = Canvas(result)
            // Draw a circle
            canvas.drawCircle(radius, radius, radius, CIRCLE_CROP_SHAPE_PAINT)
            // Draw the bitmap in the circle
            canvas.drawBitmap(toTransform, null, destRect, CIRCLE_CROP_BITMAP_PAINT)
            //Draw border
            if (borderWidth >= 1) {
                CIRCLE_CROP_BORDER_PAINT.setColor(color) //设置画笔颜色
                CIRCLE_CROP_BORDER_PAINT.setStrokeWidth(borderWidth.toFloat()) //线宽
                canvas.drawCircle(
                    radius,
                    radius,
                    radius - (borderWidth / 2),
                    CIRCLE_CROP_BORDER_PAINT
                )
            }
            canvas.setBitmap(null)
        } catch (e: Exception){
        }
        return result
    }

    private fun getAlphaSafeBitmap(
        pool: BitmapPool, maybeAlphaSafe: Bitmap
    ): Bitmap? {
        val safeConfig: Bitmap.Config = getAlphaSafeConfig(maybeAlphaSafe)!!
        if (safeConfig == maybeAlphaSafe.config) {
            return maybeAlphaSafe
        }
        val argbBitmap = pool[maybeAlphaSafe.width, maybeAlphaSafe.height, safeConfig]
        Canvas(argbBitmap).drawBitmap(maybeAlphaSafe, 0f, 0f, null /*paint*/)

        // We now own this Bitmap. It's our responsibility to replace it in the pool outside this method
        // when we're finished with it.
        return argbBitmap
    }

    private fun getAlphaSafeConfig(inBitmap: Bitmap): Bitmap.Config? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Avoid short circuiting the sdk check.
            if (Bitmap.Config.RGBA_F16 == inBitmap.config) { // NOPMD
                return Bitmap.Config.RGBA_F16
            }
        }
        return Bitmap.Config.ARGB_8888
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {}
}
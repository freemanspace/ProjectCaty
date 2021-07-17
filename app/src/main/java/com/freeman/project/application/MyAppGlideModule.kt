package com.freeman.project.application

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import okhttp3.OkHttpClient
import java.io.InputStream
import java.util.concurrent.TimeUnit

@GlideModule
class MyAppGlideModule:AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)

        //memory default 24m
        val memoryCacheSizeBytes = 1024 * 1024 * 16 // 16mb
        builder.setMemoryCache(LruResourceCache(memoryCacheSizeBytes.toLong()))

        val maxCacheSize = 16 * 1024 * 1024
        val cacheFileName = "imgCache"
        builder.setDiskCache(
            InternalCacheDiskCacheFactory(
                context, cacheFileName,
                maxCacheSize.toLong()
            )
        )
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        //super.registerComponents(context, glide, registry)

        //super.registerComponents(context, glide, registry);
        //registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());


        //super.registerComponents(context, glide, registry);
        //registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()
        val factory = OkHttpUrlLoader.Factory(client)

        glide.registry.replace(
            GlideUrl::class.java,
            InputStream::class.java, factory
        )
    }

    override fun isManifestParsingEnabled(): Boolean {
        //return super.isManifestParsingEnabled()
        //不使用清單配置，減少init時間
        return false
    }
}
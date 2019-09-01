package io.milis.sixt

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.util.Log
import com.bumptech.glide.BuildConfig
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions

@GlideModule
open class SixtGlideModule : AppGlideModule() {

    @SuppressLint("CheckResult")
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        if (BuildConfig.DEBUG) {
            builder.setLogLevel(Log.DEBUG)
            builder.setLogRequestOrigins(true)
        }
        val calculator = MemorySizeCalculator.Builder(context)
                .setBitmapPoolScreens(4f)
                .setMemoryCacheScreens(4f)
                .build()

        builder.setBitmapPool(LruBitmapPool(calculator.bitmapPoolSize.toLong()))
        builder.setMemoryCache(LruResourceCache(calculator.memoryCacheSize.toLong()))

        val defaultOptions = RequestOptions()
        val activityManager = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        defaultOptions.format(if (activityManager.isLowRamDevice) DecodeFormat.PREFER_RGB_565 else DecodeFormat.PREFER_ARGB_8888)

        builder.setDefaultRequestOptions(defaultOptions)
    }

}
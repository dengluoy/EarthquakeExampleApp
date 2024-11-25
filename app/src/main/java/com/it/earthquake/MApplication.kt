package com.it.earthquake

import android.app.Application
import android.content.Context
import com.baidu.mapapi.SDKInitializer

class MApplication : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: MApplication? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        SDKInitializer.setAgreePrivacy(this, true)
        SDKInitializer.initialize(this)
    }
}
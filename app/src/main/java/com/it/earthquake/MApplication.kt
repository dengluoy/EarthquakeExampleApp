package com.it.earthquake

import android.app.Application
import android.content.Context

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
}
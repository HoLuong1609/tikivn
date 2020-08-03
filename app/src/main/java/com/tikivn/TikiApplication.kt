package com.tikivn

import android.app.Application
import androidx.multidex.MultiDex

class TikiApplication : Application() {

    private val TAG = "TikiApplication"

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        HOLDER.INSTANCE = this
    }

    private object HOLDER {
        lateinit var INSTANCE: TikiApplication
    }

    companion object {
        val instance: TikiApplication by lazy { HOLDER.INSTANCE }
    }
}
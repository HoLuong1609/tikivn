package com.tikivn.data.prefs

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import com.tikivn.extension.PreferenceHelper

object AppPrefs {
    private val name = "tiki_prefs"
    private val LOCK = Any()

    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        if (!AppPrefs::prefs.isInitialized) {
            synchronized(LOCK) {
                prefs = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    PreferenceHelper.newEncryptPrefs(context, name)
                } else {
                    context.getSharedPreferences(name, Context.MODE_PRIVATE)
                }
            }
        }
    }
}
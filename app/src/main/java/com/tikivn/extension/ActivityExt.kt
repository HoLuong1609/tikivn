package com.tikivn.extension

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.tikivn.presentation.factory.ViewModelFactory

inline fun <reified T : ViewModel> AppCompatActivity.initViewModel() =
    ViewModelProviders.of(this, ViewModelFactory.getInstance(this)).get(T::class.java)

inline fun <reified T : ViewModel> Fragment.initViewModel() =
    ViewModelProviders.of(this, ViewModelFactory.getInstance(this.requireContext())).get(T::class.java)

/**
 * Runs a FragmentTransaction, then calls commit().
 */
inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commit()
}

fun Activity.checkPermission(): Boolean{
    val permissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    return if (ContextCompat.checkSelfPermission(this,
            Manifest.permission.CAMERA) + ContextCompat
            .checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(this, permissions, 1)
        false
    } else {
        true
    }
}

fun Activity.screenWidth(): Int {
    val displayMetrics = DisplayMetrics()
    windowManager?.defaultDisplay?.getMetrics(displayMetrics)
    return displayMetrics.widthPixels
}
package com.tikivn.extension

import android.content.Context
import android.util.DisplayMetrics
import androidx.appcompat.app.AlertDialog
import com.tikivn.R
import com.tikivn.TikiApplication

val appContext = TikiApplication.instance

fun Context.showErrorMessage(message: String, OnPositiveClick: () -> Unit) {
    AlertDialog.Builder(this)
        .setMessage(message)
        .setPositiveButton(R.string.alert_positive_button) { dialog, _ ->
            OnPositiveClick.invoke()
            dialog.dismiss()
        }
        .show()
}

fun Context.dpToDx(dp: Float) = dp * (resources
    .displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
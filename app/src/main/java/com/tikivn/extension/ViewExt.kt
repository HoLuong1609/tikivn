package com.tikivn.extension

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import com.tikivn.R

fun View.visible(){
    this.visibility = View.VISIBLE
}

fun View.invisible(){
    this.visibility = View.INVISIBLE
}

fun View.gone(){
    this.visibility = View.GONE
}

fun ImageView.load(url: String?, placeholder: Int) {
    Glide.with(context)
        .asBitmap().load(url).centerCrop()
        .placeholder(placeholder).override(width, height).diskCacheStrategy(
            DiskCacheStrategy.ALL
        )
        .into(this)
}

fun ShapeableImageView.loadShapeable(url: String?, placeholder: Int) {
    val radius = context.resources.getDimension(R.dimen.banner_radius)
    shapeAppearanceModel = shapeAppearanceModel
        .toBuilder()
        .setAllCorners(CornerFamily.ROUNDED, radius)
        .build()
    load(url, placeholder)
}

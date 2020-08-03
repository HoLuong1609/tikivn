package com.tikivn.presentation.binding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.imageview.ShapeableImageView
import com.tikivn.R
import com.tikivn.data.response.BannerResponse
import com.tikivn.extension.load
import com.tikivn.extension.loadShapeable
import com.tikivn.presentation.adapter.BannerPagerAdapter

@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, url: String?) {
    if (imageView is ShapeableImageView) {
        imageView.loadShapeable(url, R.color.gray)
    } else {
        imageView.load(url, R.color.gray)
    }
}

@BindingAdapter("app:goneUnless")
fun goneUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter(value = ["listData"])
fun <T> bindDataRecyclerView(recyclerView: RecyclerView, data: T) {
    recyclerView.adapter?.run {
        if (this is BindDataAdapter<*>) {
            (this as BindDataAdapter<T>).setData(data)
        }
    }
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter(value = ["listData"])
fun <T> bindDataBannerViewPager(viewPager: ViewPager, data: T) {
    if (data is List<*>) {
        viewPager.adapter = BannerPagerAdapter(viewPager.context, data as List<BannerResponse>)
    }
}

interface BindDataAdapter<T> {
    fun setData(data: T)
}

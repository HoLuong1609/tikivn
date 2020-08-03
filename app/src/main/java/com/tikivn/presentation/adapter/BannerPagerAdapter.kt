package com.tikivn.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.asksira.loopingviewpager.LoopingPagerAdapter
import com.google.android.material.imageview.ShapeableImageView
import com.tikivn.R
import com.tikivn.data.response.BannerResponse
import com.tikivn.extension.loadShapeable

class BannerPagerAdapter(context: Context, private val mBannerList: List<BannerResponse>) :
    LoopingPagerAdapter<BannerResponse>(context, mBannerList, true) {

    override fun bindView(convertView: View, listPosition: Int, viewType: Int) {
        val ivBanner = convertView.findViewById<ShapeableImageView>(R.id.ivBanner)
        val banner = mBannerList[listPosition]
        ivBanner.loadShapeable(banner.imageUrl, R.color.gray)
    }

    override fun inflateView(viewType: Int, container: ViewGroup, listPosition: Int): View =
        LayoutInflater.from(context).inflate(R.layout.item_banner, container, false)
}
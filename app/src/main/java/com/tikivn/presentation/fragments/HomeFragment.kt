package com.tikivn.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.tikivn.R
import com.tikivn.databinding.FragmentHomeBinding
import com.tikivn.extension.initViewModel
import com.tikivn.extension.screenWidth
import com.tikivn.presentation.adapter.CategoryAdapter
import com.tikivn.presentation.adapter.FlashDealAdapter
import com.tikivn.presentation.base.BaseFragment
import com.tikivn.presentation.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(),
    SwipeRefreshLayout.OnRefreshListener {

    override fun layoutId(): Int = R.layout.fragment_home

    override fun onCreateViewModel(): HomeViewModel = initViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            setupBannerViewPager(it.screenWidth())
            setupRecyclerView()
            swipeRefreshLayout.setOnRefreshListener(this)
        }

        bindingView.viewModel = viewModel
        onRefresh()
    }

    override fun onRefresh() {
        viewModel.fetchData()
        swipeRefreshLayout.apply {
            postDelayed({
                isRefreshing = false
            }, 100L)
        }
    }

    private fun setupBannerViewPager(screenWidth: Int) {
        val lp = vpBanner.layoutParams
        val bannerImageHeight =
            (screenWidth - 2 * resources.getDimension(R.dimen._10sdp)) / 3 + 2 * resources.getDimension(
                R.dimen._7sdp
            )
        lp.height = bannerImageHeight.toInt()
        vpBanner.layoutParams = lp
        vpBanner.offscreenPageLimit = 3
    }

    private fun setupRecyclerView() {
        rvCategoryList.layoutManager =
            GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false)
        rvCategoryList.adapter = CategoryAdapter(viewModel, this)

        rvFlashDeal.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvFlashDeal.adapter = FlashDealAdapter(viewModel, this)
    }
}
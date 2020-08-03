package com.tikivn.presentation.activities

import androidx.fragment.app.Fragment
import com.tikivn.R
import com.tikivn.databinding.ActivityMainBinding
import com.tikivn.extension.initViewModel
import com.tikivn.presentation.adapter.MainViewpagerAdapter
import com.tikivn.presentation.base.BaseActivity
import com.tikivn.presentation.fragments.HomeFragment
import com.tikivn.presentation.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {
    private lateinit var mainPageAdapter: MainViewpagerAdapter
    override fun onCreateViewModel(): MainViewModel = initViewModel()

    override fun layoutId(): Int = R.layout.activity_main

    override fun initView() {
        bindingView.viewModel = viewModel
        initMainPager()
    }

    private fun initMainPager() {
        navBottom.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    vpgMain.currentItem = Navigation.Home.index
                }
                R.id.navigation_category -> {
                    vpgMain.currentItem = Navigation.Category.index
                }
                R.id.navigation_search -> {
                    vpgMain.currentItem = Navigation.Search.index
                }
                R.id.navigation_notification -> {
                    vpgMain.currentItem = Navigation.Notification.index
                }
                R.id.navigation_my_page -> {
                    vpgMain.currentItem = Navigation.MyPage.index
                }
            }
            true
        }
        mainPageAdapter = MainViewpagerAdapter(getListFragmentPage(), supportFragmentManager)
        vpgMain.run {
            adapter = mainPageAdapter
            smoothScroll = false
            offscreenPageLimit = 3
            currentItem = Navigation.Home.index
        }
    }

    private fun getListFragmentPage(): List<Fragment> {
        return listOf(
            HomeFragment(), Fragment(),
            Fragment(), Fragment(), Fragment()
        )
    }

    enum class Navigation(var index: Int) {
        Home(0),
        Category(1),
        Search(2),
        Notification(3),
        MyPage(4)
    }
}

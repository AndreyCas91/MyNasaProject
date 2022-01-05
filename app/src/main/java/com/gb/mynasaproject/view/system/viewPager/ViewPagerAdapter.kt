package com.gb.mynasaproject.view.system.viewPager

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class ViewPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {

    private val fragments = arrayOf(MarsFragment(), EarthFragment(), VenusFragment())

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int)= fragments[position]

}
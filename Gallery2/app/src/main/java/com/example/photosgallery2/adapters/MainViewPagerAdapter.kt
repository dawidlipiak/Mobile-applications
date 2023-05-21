package com.example.photosgallery2.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.photosgallery2.fragments.GalleryFragment
import com.example.photosgallery2.fragments.PhotoFragment

class MainViewPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PhotoFragment()
            1 -> GalleryFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}
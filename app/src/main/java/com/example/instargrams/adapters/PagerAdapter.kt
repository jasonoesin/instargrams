package com.example.instargrams.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.instargrams.fragments.FollowerFragment
import com.example.instargrams.fragments.FollowingFragment

class PagerAdapter(activity: AppCompatActivity, private var username: String) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowingFragment(username)
            1 -> fragment = FollowerFragment(username)
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}
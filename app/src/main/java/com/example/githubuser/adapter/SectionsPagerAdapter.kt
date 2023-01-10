package com.example.githubuser.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuser.fragment.FollowersFragment
import com.example.githubuser.fragment.FollowersFragment.Companion.USERNAME
import com.example.githubuser.fragment.FollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity, private val username: String) : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                FollowersFragment().apply {
                    arguments = Bundle().apply {
                        putString(USERNAME, username)
                    }
                }
            }
            else -> {
                FollowingFragment().apply {
                    arguments = Bundle().apply {
                        putString(USERNAME, username)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int =  2
}
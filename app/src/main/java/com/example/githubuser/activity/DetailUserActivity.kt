package com.example.githubuser.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuser.R
import com.example.githubuser.adapter.SectionsPagerAdapter
import com.example.githubuser.databinding.ActivityDetailUserBinding
import com.example.githubuser.fragment.FollowersFragment.Companion.EXTRA_DETAIL
import com.example.githubuser.respone.DetailUserResponse
import com.example.githubuser.viewModel.MainViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity: AppCompatActivity(){

    private var _binding: ActivityDetailUserBinding? = null
    private val binding get() = _binding!!
    private val detailMainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val detailUsers = intent.extras?.get(EXTRA_DETAIL) as String
        val sectionsPagerAdapter = SectionsPagerAdapter(this, detailUsers)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabsFollow: TabLayout = binding.tabsFollows
        TabLayoutMediator(tabsFollow, viewPager) {tabs, position ->
            tabs.text = resources.getString(TAB_TITLES[position])
        }.attach()

        detailMainViewModel.detailUser.observe(this) { detailUserData ->
            getUserData(detailUserData)
        }

        detailMainViewModel.isLoading.observe(this) { loader ->
            showLoading(loader)
        }

        detailMainViewModel.getDetailUser(this, detailUsers)
    }

    private fun ImageView.loadImage(avatarUrl: String?) { Glide.with(this.context) .load(avatarUrl) .apply(
        RequestOptions().override(200,200)) .centerCrop() .circleCrop() . into(this) }

    private fun getUserData(userItems: DetailUserResponse){
        binding.apply {
            tvItemUsername.text = userItems.login
            tvItemName.text = userItems.name
            tvItemCompanyValue.text = userItems.company
            tvItemLocationValue.text = userItems.location
            tvRepoValue.text = userItems.publicRepos.toString()
            tvFollowerValue.text = userItems.followers.toString()
            tvFollowingValue.text = userItems.following.toString()
            imgItemAvatar.loadImage(userItems.avatarUrl)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    companion object {
        val TAB_TITLES = intArrayOf(
            R.string.tabs_followers,
            R.string.tabs_following
        )
    }
}

package com.example.githubuser.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.activity.DetailUserActivity
import com.example.githubuser.adapter.ListGitUserAdapter
import com.example.githubuser.databinding.FragmentFollowingBinding
import com.example.githubuser.fragment.FollowersFragment.Companion.EXTRA_DETAIL
import com.example.githubuser.fragment.FollowersFragment.Companion.USERNAME
import com.example.githubuser.respone.ItemsItem
import com.example.githubuser.viewModel.MainViewModel

class FollowingFragment : Fragment() {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private val followingViewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)

        followingViewModel.following.observe(viewLifecycleOwner) { followingData ->
            if (followingData == null) {
                val dataUsers = arguments?.getString(USERNAME) ?: ""
                followingViewModel.getFollowingData(requireActivity(), dataUsers)
            } else {
                showFollowing(followingData)
            }
        }
        followingViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        return binding.root
    }

    private fun showFollowing(dataUsers: List<ItemsItem>) {
        binding.rvFollowing.layoutManager = LinearLayoutManager(activity)
        val userAdapter = ListGitUserAdapter(dataUsers)
        binding.rvFollowing.adapter = userAdapter
        userAdapter.setOnItemClickCallback(object : ListGitUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                val intent = Intent(activity, DetailUserActivity::class.java)
                intent.putExtra(EXTRA_DETAIL, data.login)
                startActivity(intent)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
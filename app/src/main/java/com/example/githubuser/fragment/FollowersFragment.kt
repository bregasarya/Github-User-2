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
import com.example.githubuser.databinding.FragmentFollowersBinding
import com.example.githubuser.respone.ItemsItem
import com.example.githubuser.viewModel.MainViewModel

class FollowersFragment : Fragment() {
    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private val followersViewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)

        followersViewModel.followers.observe(viewLifecycleOwner) { followersData ->
            if (followersData == null) {
                val dataUsers = arguments?.getString(USERNAME)?:""
                followersViewModel.getFollowersData(requireActivity(), dataUsers)
            } else {
                showFollowers(followersData)
            }
        }
        followersViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        return binding.root
    }

    private fun showFollowers(dataUsers: List<ItemsItem>) {
        binding.rvFollowers.layoutManager = LinearLayoutManager(activity)
        val userAdapter = ListGitUserAdapter(dataUsers)
        binding.rvFollowers.adapter = userAdapter
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

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
        const val USERNAME = "bregasarya"
    }
}
package com.example.githubuser.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.respone.ItemsItem
import com.example.githubuser.adapter.ListGitUserAdapter
import com.example.githubuser.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        mainViewModel.search.observe(this) { searchData ->
            setUserData(searchData)
        }

        mainViewModel.isLoading.observe(this) { loader ->
            showLoading(loader)
        }

        mainViewModel.findItems(this)
    }

    private fun setUserData(userItems: List<ItemsItem>) {
        val userAdapter = ListGitUserAdapter(userItems)
        binding.rvUser.setHasFixedSize(true)
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.adapter = userAdapter
        userAdapter.setOnItemClickCallback( object : ListGitUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                val intent = Intent(this@MainActivity, DetailUserActivity::class.java)
                intent.putExtra(EXTRA_DETAIL, data.login)
                startActivity(intent)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.searchUserData(this@MainActivity, query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    companion object {
        private const val EXTRA_DETAIL = "extra_detail"
    }
}
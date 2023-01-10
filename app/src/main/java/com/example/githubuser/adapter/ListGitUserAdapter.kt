package com.example.githubuser.adapter


import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.databinding.ItemRowUserBinding
import com.example.githubuser.respone.ItemsItem
import kotlin.text.StringBuilder

class ListGitUserAdapter(private val listGitUser: List<ItemsItem>) : RecyclerView.Adapter<ListGitUserAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {	}

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listGitUser[position]
        holder.binding.apply {
            Glide.with(root.context)
                .load(user.avatarUrl)
                .circleCrop()
                .into(holder.binding.imgItemPhoto)
            tvItemName.text = user.login

            holder.itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(listGitUser[holder.absoluteAdapterPosition])
            }
        }
    }

    override fun getItemCount(): Int = listGitUser.size

    interface OnItemClickCallback {
        fun onItemClicked(data: ItemsItem)
    }
}
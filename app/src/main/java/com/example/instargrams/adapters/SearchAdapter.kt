package com.example.instargrams.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instargrams.databinding.ItemSearchUserBinding
import com.example.instargrams.models.SearchUser
import com.squareup.picasso.Picasso

class SearchAdapter(private val items: List<SearchUser>) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private lateinit var binding: ItemSearchUserBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding= ItemSearchUserBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    //View Holder
    inner class ViewHolder(itemView: ItemSearchUserBinding) : RecyclerView.ViewHolder(itemView.root){
        fun bind(item: SearchUser){
            binding.apply {
                if (item.avatar_url.isNotEmpty()) {
                    Picasso.get().load(item.avatar_url).into(avatarUrl)
                }
                login.text = item.login
            }
        }
    }
}
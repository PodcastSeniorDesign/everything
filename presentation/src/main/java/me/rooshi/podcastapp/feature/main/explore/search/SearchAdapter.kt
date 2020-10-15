package me.rooshi.podcastapp.feature.main.explore.search

import android.view.ViewGroup
import me.rooshi.podcastapp.common.base.MyAdapter
import me.rooshi.podcastapp.common.base.MyViewHolder
import me.rooshi.podcastapp.databinding.SearchItemBinding
import javax.inject.Inject

class SearchAdapter @Inject constructor(

) : MyAdapter<SearchItem, SearchItemBinding>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder<SearchItemBinding> {
        return MyViewHolder(parent, SearchItemBinding::inflate)
    }

    override fun onBindViewHolder(holder: MyViewHolder<SearchItemBinding>, position: Int) {

    }
}
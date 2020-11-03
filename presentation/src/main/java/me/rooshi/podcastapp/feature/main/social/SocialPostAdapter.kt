package me.rooshi.podcastapp.feature.main.social

import android.view.ViewGroup
import me.rooshi.podcastapp.common.base.MyAdapter
import me.rooshi.podcastapp.common.base.MyViewHolder
import me.rooshi.podcastapp.databinding.SocialPostItemBinding
import javax.inject.Inject

class SocialPostAdapter @Inject constructor() : MyAdapter<SocialPostItem, SocialPostItemBinding>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder<SocialPostItemBinding> {
        return MyViewHolder(parent, SocialPostItemBinding::inflate).apply {

        }
    }

    override fun onBindViewHolder(holder: MyViewHolder<SocialPostItemBinding>, position: Int) {
        val result = getItem(position)
    }

}
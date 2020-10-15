package me.rooshi.podcastapp.feature.main.explore.search

import android.view.ViewGroup
import com.squareup.picasso.Picasso
import me.rooshi.podcastapp.common.Navigator
import me.rooshi.podcastapp.common.base.MyAdapter
import me.rooshi.podcastapp.common.base.MyViewHolder
import me.rooshi.podcastapp.databinding.SearchItemBinding
import javax.inject.Inject

class SearchAdapter @Inject constructor(
        private val navigator: Navigator
) : MyAdapter<SearchItem, SearchItemBinding>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder<SearchItemBinding> {
        return MyViewHolder(parent, SearchItemBinding::inflate).apply {
            binding.root.setOnClickListener {
                val result = getItem(adapterPosition)
                navigator.showPodcast(result.podcast)
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder<SearchItemBinding>, position: Int) {
        val result = getItem(position)

        holder.binding.title.text = result.podcast.title
        holder.binding.publisher.text = result.podcast.publisher
        Picasso.get().load(result.podcast.thumbnailURL).into(holder.binding.coverArtImageView)
    }
}
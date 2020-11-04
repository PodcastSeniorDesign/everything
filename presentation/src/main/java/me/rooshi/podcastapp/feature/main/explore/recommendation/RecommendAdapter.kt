package me.rooshi.podcastapp.feature.main.explore.recommendation

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import me.rooshi.domain.model.Podcast
import me.rooshi.domain.repository.PlayerRepository
import me.rooshi.podcastapp.common.Navigator
import me.rooshi.podcastapp.common.base.MyAdapter
import me.rooshi.podcastapp.common.base.MyViewHolder
import me.rooshi.podcastapp.databinding.RecommendItemBinding
import javax.inject.Inject

class RecommendAdapter @Inject constructor(
        private val navigator: Navigator
) : MyAdapter<RecommendItem, RecommendItemBinding>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder<RecommendItemBinding> {
        return MyViewHolder(parent, RecommendItemBinding::inflate).apply {
            binding.root.setOnClickListener {
                val result = getItem(adapterPosition)
                navigator.showPodcast(result.podcast)
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder<RecommendItemBinding>, position: Int) {
        val result = getItem(position)

        holder.binding.title.text = result.podcast.title
        Picasso.get().load(result.podcast.thumbnailURL).into(holder.binding.imageView3)
    }

}
package me.rooshi.podcastapp.feature.main.episodeList

import android.view.ViewGroup
import com.squareup.picasso.Picasso
import me.rooshi.domain.repository.PlayerRepository
import me.rooshi.podcastapp.common.base.MyAdapter
import me.rooshi.podcastapp.common.base.MyViewHolder
import me.rooshi.podcastapp.databinding.EpisodeItemBinding
import me.rooshi.podcastapp.feature.main.player.PlayerController
import javax.inject.Inject

class EpisodeAdapter @Inject constructor(
    private val playerRepository: PlayerRepository
) : MyAdapter<EpisodeItem, EpisodeItemBinding>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder<EpisodeItemBinding> {
        return MyViewHolder(parent, EpisodeItemBinding::inflate).apply {
            binding.root.setOnClickListener {
                val result = getItem(adapterPosition)
                playerRepository.changeEpisode(result.episode)
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder<EpisodeItemBinding>, position: Int) {
        val result = getItem(position)

        holder.binding.title.text = result.episode.title
        holder.binding.description.text = result.episode.description
        Picasso.get().load(result.episode.thumbnailURL).into(holder.binding.coverArtImageView)
    }

}
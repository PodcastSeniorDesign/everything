package me.rooshi.podcastapp.feature.main.episodeList

import android.text.Html
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import me.rooshi.domain.repository.PlayerRepository
import me.rooshi.podcastapp.common.base.MyAdapter
import me.rooshi.podcastapp.common.base.MyViewHolder
import me.rooshi.podcastapp.databinding.EpisodeItemBinding
import me.rooshi.podcastapp.feature.main.player.PlayerController
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import org.jsoup.Jsoup

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
        holder.binding.description.text = Jsoup.parse(result.episode.description).text()

        val format = SimpleDateFormat("MMMM d, yyyy")
        val date = Date(result.episode.dateMilli)
        holder.binding.date.text = format.format(date)

        var lengthMinutes = TimeUnit.SECONDS.toMinutes(result.episode.lengthSeconds.toLong());
        holder.binding.length.text = String.format("%d MIN", lengthMinutes)
        Picasso.get().load(result.episode.thumbnailURL).into(holder.binding.coverArtImageView)
    }

}
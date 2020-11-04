package me.rooshi.podcastapp.feature.main.subscriptions

import android.view.ViewGroup
import com.squareup.picasso.Picasso
import me.rooshi.domain.repository.PlayerRepository
import me.rooshi.podcastapp.common.base.MyAdapter
import me.rooshi.podcastapp.common.base.MyViewHolder
import me.rooshi.podcastapp.databinding.SubscriptionEpisodeItemBinding
import org.jsoup.Jsoup
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class SubscriptionEpisodeAdapter @Inject constructor(
        private val playerRepository: PlayerRepository
) : MyAdapter<SubscriptionEpisodeItem, SubscriptionEpisodeItemBinding>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder<SubscriptionEpisodeItemBinding> {
        return MyViewHolder(parent, SubscriptionEpisodeItemBinding::inflate).apply {
            binding.root.setOnClickListener {
                val result = getItem(adapterPosition)
                playerRepository.changeEpisode(result.episode)
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder<SubscriptionEpisodeItemBinding>, position: Int) {
        val result = getItem(position)

        holder.binding.title.text = result.episode.title
        holder.binding.description.text = Jsoup.parse(result.episode.description).text()

        val format = SimpleDateFormat("MMMM d, yyyy")
        val date = Date(result.episode.dateMilli)
        holder.binding.date.text = format.format(date)
        Picasso.get().load(result.episode.thumbnailURL).into(holder.binding.coverArtImageView)
    }
}
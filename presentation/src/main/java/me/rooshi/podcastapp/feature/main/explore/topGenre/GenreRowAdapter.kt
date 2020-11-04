package me.rooshi.podcastapp.feature.main.explore.topGenre

import android.view.ViewGroup
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import me.rooshi.domain.model.Episode
import me.rooshi.domain.model.GenreRowItem
import me.rooshi.podcastapp.common.base.MyAdapter
import me.rooshi.podcastapp.common.base.MyViewHolder
import me.rooshi.podcastapp.common.util.extensions.forwardTouches
import me.rooshi.podcastapp.databinding.GenreRowItemBinding
import me.rooshi.podcastapp.feature.main.player.PlayerController
import javax.inject.Inject

class GenreRowAdapter @Inject constructor(

) : MyAdapter<GenreRowItem, GenreRowItemBinding>() {

    val clickIntent: Subject<Episode> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder<GenreRowItemBinding> {
        return MyViewHolder(parent, GenreRowItemBinding::inflate).apply {
            binding.epRV.adapter = GenreEpisodeAdapter(clickIntent)

            binding.epRV.forwardTouches(binding.root)
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder<GenreRowItemBinding>, position: Int) {
        val item = getItem(position)

        holder.binding.genreName.text = item.genre
        (holder.binding.epRV.adapter as GenreEpisodeAdapter).data = item.episodes
    }


}
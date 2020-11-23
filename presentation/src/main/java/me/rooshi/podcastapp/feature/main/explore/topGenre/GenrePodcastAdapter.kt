package me.rooshi.podcastapp.feature.main.explore.topGenre

import android.view.ViewGroup
import com.squareup.picasso.Picasso
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import me.rooshi.domain.model.Episode
import me.rooshi.domain.model.Podcast
import me.rooshi.podcastapp.common.Navigator
import me.rooshi.podcastapp.common.base.MyAdapter
import me.rooshi.podcastapp.common.base.MyViewHolder
import me.rooshi.podcastapp.databinding.RecommendItemBinding
import me.rooshi.podcastapp.feature.main.player.PlayerController
import javax.inject.Inject

class GenrePodcastAdapter constructor(
        private val subject: Subject<Podcast>
): MyAdapter<Podcast, RecommendItemBinding>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder<RecommendItemBinding> {
        return MyViewHolder(parent, RecommendItemBinding::inflate).apply {
            binding.root.setOnClickListener {
                val result = getItem(adapterPosition)
                subject.onNext(result)
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder<RecommendItemBinding>, position: Int) {
        val result = getItem(position)

        holder.binding.title.text = result.title
        Picasso.get().load(result.thumbnailURL).into(holder.binding.imageView3)


    }
}
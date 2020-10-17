package me.rooshi.podcastapp.feature.main.podcastInfo

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import com.jakewharton.rxbinding4.view.ViewScrollChangeEvent
import com.jakewharton.rxbinding4.view.scrollChangeEvents
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import me.rooshi.podcastapp.common.base.MyThemedActivity
import me.rooshi.podcastapp.common.util.extensions.viewBinding
import me.rooshi.podcastapp.databinding.PodcastInfoActivityBinding
import me.rooshi.podcastapp.feature.main.episodeList.EpisodeAdapter
import javax.inject.Inject

@AndroidEntryPoint
class PodcastInfoActivity constructor() : MyThemedActivity(), PodcastInfoView {

    @Inject lateinit var episodeAdapter: EpisodeAdapter

    override val onNewIntentIntent: Subject<Intent> = PublishSubject.create()
    override val bottomScrollReachedIntent: Observable<ViewScrollChangeEvent> by lazy { binding.episodeRV.scrollChangeEvents() }

    private val binding by viewBinding(PodcastInfoActivityBinding::inflate)
    private val viewModel: PodcastInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.bindView(this)

        onNewIntentIntent.onNext(intent)

        binding.episodeRV.adapter = episodeAdapter
    }

    override fun render(state: PodcastInfoState) {
        if (state.podcast != null) {
            //shouldn't be loading the image all the time
            if (binding.image.drawable == null && !state.podcast.imageURL.isNullOrEmpty()) {
                Picasso.get().load(state.podcast.imageURL).into(binding.image)
            }
            binding.title.text = state.podcast.title
            binding.publisher.text = state.podcast.publisher
            binding.description.text = state.podcast.description
            binding.numEpisodes.text = "${state.podcast.totalEpisodes} episodes:"
        }
        episodeAdapter.data = state.episodes
        episodeAdapter.notifyDataSetChanged()
    }

}
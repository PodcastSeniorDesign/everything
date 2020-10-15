package me.rooshi.podcastapp.feature.main.podcastInfo

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import autodispose2.androidx.lifecycle.scope
import autodispose2.autoDispose
import com.google.gson.Gson
import me.rooshi.domain.model.Podcast
import me.rooshi.podcastapp.common.base.MyViewModel

class PodcastInfoViewModel @ViewModelInject constructor(
        private val gson: Gson
) : MyViewModel<PodcastInfoView, PodcastInfoState>(PodcastInfoState()) {

    override fun bindView(view: PodcastInfoView) {
        super.bindView(view)

        view.onNewIntentIntent
                .autoDispose(view.scope())
                .subscribe {intent ->
                    val podcast = gson.fromJson<Podcast>(intent.getStringExtra("podcast"), Podcast::class.java)
                    newState { copy(podcast = podcast) }
                }

    }
}
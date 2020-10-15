package me.rooshi.podcastapp.feature.main.podcastInfo

import android.content.Intent
import io.reactivex.rxjava3.subjects.Subject
import me.rooshi.podcastapp.common.base.MyView

interface PodcastInfoView : MyView<PodcastInfoState> {
    val onNewIntentIntent: Subject<Intent>
}
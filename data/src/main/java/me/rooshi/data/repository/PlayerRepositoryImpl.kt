package me.rooshi.data.repository

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.util.Log
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.Subject
import me.rooshi.domain.model.Episode
import me.rooshi.domain.model.Podcast
import me.rooshi.domain.repository.PlayerRepository
import java.io.IOException
import javax.inject.Inject

class PlayerRepositoryImpl @Inject constructor(
) : PlayerRepository {

    override val currentEpisode: Subject<Episode> = BehaviorSubject.create()

    override fun loadPodcastInfo() {
        TODO("Not yet implemented")
    }

    override fun changeEpisode(episode: Episode) {
        currentEpisode.onNext(episode)
    }


}
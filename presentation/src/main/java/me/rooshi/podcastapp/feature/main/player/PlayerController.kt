package me.rooshi.podcastapp.feature.main.player

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import androidx.core.os.postDelayed
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.Subject
import me.rooshi.domain.model.Episode
import java.io.IOException

class PlayerController {

    private val mediaPlayer = MediaPlayer()
    private var playing = Episode()

    val timerIntent : Subject<Int> = BehaviorSubject.create()

    init {
        setMediaPlayerAttributes()

        //TODO turn on and off with pause and play and run faster than 1 second
        val handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                    timerIntent.onNext(mediaPlayer.currentPosition)
                    handler.postDelayed(this, 1000)
            }
        })
    }

    private fun setMediaPlayerAttributes() {
        mediaPlayer.setAudioAttributes(
                AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                        .build())
    }


    fun loadEpisode(episode: Episode) : Observable<Boolean> {
        playing = episode
        return Observable.create { emitter ->
            emitter.onNext(false)
            try {
                mediaPlayer.stop()
                mediaPlayer.reset()

                mediaPlayer.setDataSource(episode.audioURL)

                mediaPlayer.setOnPreparedListener {
                    emitter.onNext(true)
                }
                mediaPlayer.prepareAsync()
            } catch (e: IOException) {
                emitter.onError(e)
            }
        }
    }

    fun play() {
        mediaPlayer.start()
    }

    fun pause() {
        mediaPlayer.pause()
    }

    fun rewind() {
        mediaPlayer.seekTo(mediaPlayer.currentPosition - 15 * 1000)
    }

    fun forward() {
        mediaPlayer.seekTo(mediaPlayer.currentPosition + 15 * 1000)
    }

    fun seekTo(millis: Int) {
        mediaPlayer.seekTo(millis)
        play()
    }

    fun getDuration() : Int {
        return mediaPlayer.duration
    }

    fun setPlaybackSpeed(speed: Float) {
        val old = mediaPlayer.playbackParams
        old.speed = speed
        mediaPlayer.playbackParams = old
    }
}
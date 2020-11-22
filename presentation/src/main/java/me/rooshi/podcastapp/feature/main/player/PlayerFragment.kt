package me.rooshi.podcastapp.feature.main.player

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.SeekBarChangeEvent
import com.jakewharton.rxbinding4.widget.changeEvents
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import me.rooshi.domain.repository.PlayerRepository
import me.rooshi.podcastapp.R
import me.rooshi.podcastapp.common.base.MyFragment
import me.rooshi.podcastapp.common.util.extensions.viewBinding
import me.rooshi.podcastapp.databinding.PlayerFragmentBinding
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class PlayerFragment : MyFragment(R.layout.player_fragment), PlayerView {

    @Inject lateinit var playerRepository: PlayerRepository

    //private val playbackSpeedDialog by lazy { PlaybackSpeedDialog() }

    //override val finishedLoadingIntent: Observable<Boolean> by lazy { playerController.initMediaPlayer() }
    override val playPauseIntent: Observable<Unit> by lazy { binding.playPause.clicks() }
    override val rewindIntent: Observable<Unit> by lazy { binding.rewind15.clicks() }
    override val forwardIntent: Observable<Unit> by lazy { binding.forward15.clicks() }
    override val timerIntent: Observable<Int> by lazy { playerController.timerIntent }
    override val seekIntent: Observable<SeekBarChangeEvent> by lazy { binding.seekBar.changeEvents() }
    override val speedChangeClickIntent: Observable<Unit> by lazy { binding.playbackSpeed.clicks() }
    override val speedChangeSubject: Subject<Float> = PublishSubject.create()

    @Inject lateinit var playerController: PlayerController

    private val binding by viewBinding(PlayerFragmentBinding::bind)
    private val viewModel : PlayerViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.player_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()
        viewModel.bindView(this)
    }

    override fun render(state: PlayerState) {
        if (state.playing) {
            binding.playPause.setImageResource(R.drawable.ic_round_pause_circle_filled_24)
        } else {
            binding.playPause.setImageResource(R.drawable.ic_round_play_circle_filled_24)
        }

        binding.playPause.isEnabled = state.episodeLoaded
        binding.playbackSpeed.isEnabled = state.episodeLoaded

        if (state.episode.imageURL.isNotEmpty()) {
            Picasso.get().load(state.episode.imageURL).into(binding.coverArtImageView)
        }
        binding.episodeName.text = state.episode.title
        binding.podcastName.text = state.episode.podcast.title

        val format = SimpleDateFormat("MMMM d, yyyy")
        val date = Date(state.episode.dateMilli)
        if (state.episode.dateMilli != 0L) {
            binding.episodeDate.text = format.format(date)
        } else {
            binding.episodeDate.text = ""
        }
        binding.previewName.text = state.episode.title

        if (state.episodeLoaded) {
            binding.seekBar.max = state.episode.lengthSeconds*1000

            val timersTimes = changeMillisToText(state.timer, state.episode.lengthSeconds*1000)
            binding.currentTime.text = timersTimes[0]
            binding.timeLeft.text = timersTimes[1]

            binding.playPause.alpha = 1f

        } else {

            binding.playPause.alpha = 0.6f

        }
    }

    override fun showPlaybackSpeedDialog() {
        val dialog = PlaybackSpeedDialog(speedChangeSubject)
        dialog.show(parentFragmentManager, "playbackSpeedDialog")
    }

    private fun changeMillisToText(millis: Int, length: Int): List<String> {
        val hours: Int = millis / (1000 * 60 * 60)
        val minutes: Int = millis % (1000 * 60 * 60) / (1000 * 60)
        val seconds: Int = millis % (1000 * 60 * 60) % (1000 * 60) / 1000

        val millis2 = length - millis
        val hours2 = millis2 / (1000 * 60 * 60)
        val minutes2 = millis2 % (1000 * 60 * 60) / (1000 * 60)
        val seconds2 = millis2 % (1000 * 60 * 60) % (1000 * 60) / 1000

        return listOf(String.format("%02d:%02d:%02d", hours, minutes, seconds), String.format("-%02d:%02d:%02d", hours2, minutes2, seconds2))
    }
}
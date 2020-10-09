package me.rooshi.podcastapp.feature.main.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.SeekBarChangeEvent
import com.jakewharton.rxbinding4.widget.changeEvents
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.core.Observable
import me.rooshi.domain.repository.PlayerRepository
import me.rooshi.podcastapp.R
import me.rooshi.podcastapp.common.base.MyFragment
import me.rooshi.podcastapp.common.util.extensions.viewBinding
import me.rooshi.podcastapp.databinding.PlayerFragmentBinding
import javax.inject.Inject

@AndroidEntryPoint
class PlayerFragment : MyFragment(R.layout.player_fragment), PlayerView {

    @Inject lateinit var playerRepository: PlayerRepository

    override val playPauseIntent: Observable<Unit> by lazy { binding.playPause.clicks() }
    override val rewindIntent: Observable<Unit> by lazy { binding.rewind15.clicks() }
    override val forwardIntent: Observable<Unit> by lazy { binding.forward15.clicks() }
    override val timerIntent: Observable<Int> by lazy { playerController.timer }
    override val seekIntent: Observable<SeekBarChangeEvent> by lazy { binding.seekBar.changeEvents() }

    @Inject lateinit var playerController: PlayerController

    private val binding by viewBinding(PlayerFragmentBinding::bind)
    private val viewModel : PlayerViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.player_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()
        viewModel.bindView(this)
    }

    override fun render(state: PlayerState) {
        if (state.playing) {
            binding.playPause.setImageResource(R.drawable.pause)
        } else {
            binding.playPause.setImageResource(R.drawable.play)
        }

        binding.playPause.isEnabled = state.episodeLoaded

        binding.episodeName.text = state.episode?.name ?: ""
        binding.podcastName.text = state.episode?.podcast?.name ?: ""
        binding.episodeDate.text = state.episode?.date ?: ""

        if (state.episodeLoaded) {
            binding.seekBar.max = state.episodeLength

            val timersTimes = changeMillisToText(state.timer, state.episodeLength)
            binding.currentTime.text = timersTimes[0]
            binding.timeLeft.text = timersTimes[1]

            binding.playPause.alpha = 1f

        } else {

            binding.playPause.alpha = 0.6f

        }
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
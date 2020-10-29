package me.rooshi.podcastapp.feature.main.player

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import dagger.hilt.android.AndroidEntryPoint
import me.rooshi.podcastapp.R
import me.rooshi.podcastapp.common.Navigator
import javax.inject.Inject

@AndroidEntryPoint
class PlaybackSpeedDialog @Inject constructor(
        private val playerController: PlayerController
): DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder : AlertDialog.Builder = activity.let { AlertDialog.Builder(it) }
        builder.setTitle("Choose Playback Speed")
                .setSingleChoiceItems(R.array.speed_dialog_options, -1,
                DialogInterface.OnClickListener { dialog, which ->
                    val array = resources.getStringArray(R.array.speed_dialog_options)
                    playerController.setPlaybackSpeed(array[which].toFloat())
                })
    return builder.create()
    }
}
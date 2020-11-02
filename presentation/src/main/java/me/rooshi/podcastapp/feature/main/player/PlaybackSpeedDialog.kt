package me.rooshi.podcastapp.feature.main.player

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.subjects.Subject
import me.rooshi.podcastapp.R
import me.rooshi.podcastapp.common.Navigator
import javax.inject.Inject

@AndroidEntryPoint
class PlaybackSpeedDialog @Inject constructor(
        //private val playerController: PlayerController
        private val speedChangeSubject: Subject<Float>
): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context)
                .setTitle("Choose Playback Speed")
                .setSingleChoiceItems(R.array.speed_dialog_options, -1,
                        DialogInterface.OnClickListener { _, which ->
                            val array = resources.getStringArray(R.array.speed_dialog_options)
                            speedChangeSubject.onNext(array[which].toFloat())
                        }).create()
    }
}
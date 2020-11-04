package me.rooshi.podcastapp.feature.main.social.newPost

import android.os.Bundle
import androidx.activity.viewModels
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChanges
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import me.rooshi.podcastapp.common.base.MyThemedActivity
import me.rooshi.podcastapp.common.util.extensions.viewBinding
import me.rooshi.podcastapp.databinding.AddFriendActivityBinding
import me.rooshi.podcastapp.databinding.NewPostActivityBinding

@AndroidEntryPoint
class NewPostActivity constructor() : MyThemedActivity(), NewPostView  {

    override val postTextChanged: Observable<CharSequence> by lazy { binding.textInputEditText.textChanges() }
    override val onNewIntentIntent: Subject<Unit> = PublishSubject.create()
    override val createPostIntent: Observable<Unit> by lazy { binding.button2.clicks() }

    private val binding by viewBinding(NewPostActivityBinding::inflate)
    private val viewModel: NewPostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.bindView(this)

        onNewIntentIntent.onNext(Unit)
    }

    override fun onResume() {
        super.onResume()
        onNewIntentIntent.onNext(Unit)
    }

    override fun render(state: NewPostState) {
        if (state.finished) {
            finish()
        }
    }

}
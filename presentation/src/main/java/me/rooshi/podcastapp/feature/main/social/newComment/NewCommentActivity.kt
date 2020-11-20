package me.rooshi.podcastapp.feature.main.social.newComment

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChanges
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import me.rooshi.podcastapp.common.base.MyThemedActivity
import me.rooshi.podcastapp.common.util.extensions.viewBinding
import me.rooshi.podcastapp.databinding.NewCommentActivityBinding
import javax.inject.Inject

@AndroidEntryPoint
class NewCommentActivity : MyThemedActivity(), NewCommentView {

    override val onNewIntentIntent: Subject<Intent> = PublishSubject.create()
    override val textChanges: Observable<CharSequence> by lazy { binding.textInputEditText.textChanges() }
    override val postComment: Observable<Unit> by lazy { binding.button2.clicks() }

    private val binding by viewBinding(NewCommentActivityBinding::inflate)
    private val viewModel: NewCommentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.bindView(this)

        onNewIntentIntent.onNext(intent)
    }

    override fun render(state: NewCommentState) {
        if (state.finished) {
            finish()
            return
        }

        binding.postText.text = state.post.bodyText
        binding.userText.text = state.post.userId + " has made a post"
    }

}
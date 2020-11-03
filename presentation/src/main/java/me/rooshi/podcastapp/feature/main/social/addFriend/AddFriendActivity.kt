package me.rooshi.podcastapp.feature.main.social.addFriend

import android.os.Bundle
import androidx.activity.viewModels
import com.jakewharton.rxbinding4.view.clicks
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import me.rooshi.podcastapp.common.base.MyThemedActivity
import me.rooshi.podcastapp.common.util.extensions.viewBinding
import me.rooshi.podcastapp.databinding.AddFriendActivityBinding
import me.rooshi.podcastapp.databinding.FavoriteActivityBinding

@AndroidEntryPoint
class AddFriendActivity : MyThemedActivity(), AddFriendView  {

    private val addFriendAdapter = AddFriendAdapter()

    override val onNewIntentIntent: Subject<Unit> = PublishSubject.create()
    override val finishedIntent: Observable<Unit> by lazy { binding.finishedButton.clicks() }
    override val addFriendCheckedIntent: Observable<AddFriendItem> = addFriendAdapter.addFriendItemClick


    private val binding by viewBinding(AddFriendActivityBinding::inflate)
    private val viewModel: AddFriendViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        viewModel.bindView(this)

        binding.genreRV.adapter = addFriendAdapter

        onNewIntentIntent.onNext(Unit)
    }

    override fun render(state: AddFriendState) {
        if (state.finished) {
            finish()
        }

        addFriendAdapter.data = state.users
    }
}
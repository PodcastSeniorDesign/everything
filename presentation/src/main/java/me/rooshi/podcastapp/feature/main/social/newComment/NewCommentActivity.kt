package me.rooshi.podcastapp.feature.main.social.newComment

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import me.rooshi.podcastapp.common.base.MyThemedActivity
import me.rooshi.podcastapp.common.util.extensions.viewBinding
import me.rooshi.podcastapp.databinding.NewCommentActivityBinding
import javax.inject.Inject

@AndroidEntryPoint
class NewCommentActivity constructor() : MyThemedActivity(), NewCommentView {

    private val binding by viewBinding(NewCommentActivityBinding::inflate)
    private val viewModel: NewCommentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(binding.root)
        viewModel.bindView(this)
    }

    override fun render(state: NewCommentState) {

    }

}
package me.rooshi.podcastapp.feature.main.social.newComment

import androidx.hilt.lifecycle.ViewModelInject
import me.rooshi.podcastapp.common.base.MyViewModel

class NewCommentViewModel @ViewModelInject constructor(

) : MyViewModel<NewCommentView, NewCommentState>(NewCommentState()) {

    override fun bindView(view: NewCommentView) {
        super.bindView(view)


    }
}
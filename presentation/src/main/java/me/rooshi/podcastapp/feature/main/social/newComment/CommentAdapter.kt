package me.rooshi.podcastapp.feature.main.social.newComment

import android.view.ViewGroup
import me.rooshi.domain.model.Comment
import me.rooshi.podcastapp.common.base.MyAdapter
import me.rooshi.podcastapp.common.base.MyViewHolder
import me.rooshi.podcastapp.databinding.SocialCommentItemBinding

class CommentAdapter : MyAdapter<Comment, SocialCommentItemBinding>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder<SocialCommentItemBinding> {
        return MyViewHolder(parent, SocialCommentItemBinding::inflate)
    }

    override fun onBindViewHolder(holder: MyViewHolder<SocialCommentItemBinding>, position: Int) {
        val result = getItem(position)

        holder.binding.userText.text = result.user + " commented"
        holder.binding.postText.text = result.text
    }

}
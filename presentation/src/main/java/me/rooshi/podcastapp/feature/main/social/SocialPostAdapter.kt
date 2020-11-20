package me.rooshi.podcastapp.feature.main.social

import android.view.ViewGroup
import me.rooshi.podcastapp.common.Navigator
import me.rooshi.podcastapp.common.base.MyAdapter
import me.rooshi.podcastapp.common.base.MyViewHolder
import me.rooshi.podcastapp.databinding.SocialPostItemBinding
import me.rooshi.podcastapp.feature.main.social.newComment.CommentAdapter
import javax.inject.Inject

class SocialPostAdapter @Inject constructor(
        private val navigator: Navigator
) : MyAdapter<SocialPostItem, SocialPostItemBinding>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder<SocialPostItemBinding> {
        return MyViewHolder(parent, SocialPostItemBinding::inflate).apply {
            binding.RV.adapter = CommentAdapter()

            binding.button3.setOnClickListener {
                navigator.showNewCommentActivity(getItem(adapterPosition).post)
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder<SocialPostItemBinding>, position: Int) {
        val result = getItem(position)
        holder.binding.postText.text = result.post.bodyText
        holder.binding.userText.text = result.post.userId + " has made a post"

        (holder.binding.RV.adapter as CommentAdapter).data = result.post.comments
    }

}
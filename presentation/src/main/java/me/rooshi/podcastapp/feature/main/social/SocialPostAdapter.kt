package me.rooshi.podcastapp.feature.main.social

import android.view.ViewGroup
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import me.rooshi.domain.repository.UserRepository
import me.rooshi.podcastapp.R
import me.rooshi.podcastapp.common.Navigator
import me.rooshi.podcastapp.common.base.MyAdapter
import me.rooshi.podcastapp.common.base.MyViewHolder
import me.rooshi.podcastapp.databinding.SocialPostItemBinding
import me.rooshi.podcastapp.feature.main.social.addFriend.AddFriendItem
import me.rooshi.podcastapp.feature.main.social.newComment.CommentAdapter
import javax.inject.Inject

class SocialPostAdapter @Inject constructor(
        private val navigator: Navigator,
        private val userRepository: UserRepository
) : MyAdapter<SocialPostItem, SocialPostItemBinding>() {

    val likedChangeIntent: Subject<Unit> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder<SocialPostItemBinding> {
        return MyViewHolder(parent, SocialPostItemBinding::inflate).apply {
            binding.RV.adapter = CommentAdapter()

            binding.likeButton.setOnClickListener {
                if (getItem(adapterPosition).post.likes.contains(userRepository.getUser().id)) {
                    userRepository.unlikePost(getItem(adapterPosition).post.id)
                    getItem(adapterPosition).post.likes.remove(userRepository.getUser().id)
                } else {
                    userRepository.likePost(getItem(adapterPosition).post.id)
                    getItem(adapterPosition).post.likes.add(userRepository.getUser().id)
                }
                likedChangeIntent.onNext(Unit)
            }

            binding.commentButton.setOnClickListener {
                navigator.showNewCommentActivity(getItem(adapterPosition).post)
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder<SocialPostItemBinding>, position: Int) {
        val result = getItem(position)

        holder.binding.post.text = result.post.bodyText
        holder.binding.user.text = result.post.user


        val found = result.post.likes.contains(userRepository.getUser().id)
        if (found) {
            holder.binding.likeButton.setImageResource(R.drawable.ic_favorite_white_24dp)
        } else {
            holder.binding.likeButton.setImageResource(R.drawable.ic_favorite_border_24)
        }
        holder.binding.likeCountView.text = "${result.post.likes.size} Likes"

        if (result.post.comments.size == 1) {
            holder.binding.commentCount.text = "1 Reply"
        } else {
            holder.binding.commentCount.text = "${result.post.comments.size} Replies"
        }

        (holder.binding.RV.adapter as CommentAdapter).data = result.post.comments
    }

}
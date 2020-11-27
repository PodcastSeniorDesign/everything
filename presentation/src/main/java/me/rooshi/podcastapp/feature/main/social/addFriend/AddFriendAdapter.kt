package me.rooshi.podcastapp.feature.main.social.addFriend

import android.view.ViewGroup
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import me.rooshi.domain.repository.UserRepository
import me.rooshi.podcastapp.common.base.MyAdapter
import me.rooshi.podcastapp.common.base.MyViewHolder
import me.rooshi.podcastapp.databinding.AddFriendItemBinding
import javax.inject.Inject

class AddFriendAdapter : MyAdapter<AddFriendItem, AddFriendItemBinding>() {

    val toggleFriendItemClick: Subject<AddFriendItem> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder<AddFriendItemBinding> {
        return MyViewHolder(parent, AddFriendItemBinding::inflate).apply {

            binding.friendButton.setOnClickListener {
                val result = getItem(adapterPosition)
                toggleFriendItemClick.onNext(result)
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder<AddFriendItemBinding>, position: Int) {
        val result = getItem(position)

        holder.binding.email.text = result.user.email

        if (result.user.displayName == "null") {
            holder.binding.name.text = result.user.id
        } else {
            holder.binding.name.text = result.user.displayName
        }

        if (result.status) {
            holder.binding.friendButton.text = "Unfriend"
        } else {
            holder.binding.friendButton.text = "Friend"
        }
    }

}
package me.rooshi.podcastapp.feature.main.social.addFriend

import android.view.ViewGroup
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import me.rooshi.podcastapp.common.base.MyAdapter
import me.rooshi.podcastapp.common.base.MyViewHolder
import me.rooshi.podcastapp.databinding.AddFriendItemBinding

class AddFriendAdapter : MyAdapter<AddFriendItem, AddFriendItemBinding>() {

    val addFriendItemClick: Subject<AddFriendItem> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder<AddFriendItemBinding> {
        return MyViewHolder(parent, AddFriendItemBinding::inflate).apply {
            binding.friendButton.setOnClickListener {
                addFriendItemClick.onNext(getItem(adapterPosition))
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder<AddFriendItemBinding>, position: Int) {
        val result = getItem(position)

        holder.binding.textView.text = result.user.id
        //holder.binding.imageView2.setImageResource(result.user.)
    }

}
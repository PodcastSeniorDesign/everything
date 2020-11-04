package me.rooshi.podcastapp.feature.favoriteGenre

import android.view.ViewGroup
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import me.rooshi.podcastapp.common.base.MyAdapter
import me.rooshi.podcastapp.common.base.MyViewHolder
import me.rooshi.podcastapp.databinding.FavoriteGenreItemBinding

class FavoriteGenreAdapter : MyAdapter<FavoriteGenreItem, FavoriteGenreItemBinding>() {

    val favoriteGenreItemClick: Subject<FavoriteGenreItem> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder<FavoriteGenreItemBinding> {
        return MyViewHolder(parent, FavoriteGenreItemBinding::inflate).apply {
            binding.checkBox.setOnClickListener {
                favoriteGenreItemClick.onNext(getItem(adapterPosition))
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder<FavoriteGenreItemBinding>, position: Int) {
        val result = getItem(position)

        holder.binding.textView.text = result.genre.name
        holder.binding.imageView2.setImageResource(result.genre.drawable)
        holder.binding.checkBox.isChecked = result.clicked
    }

}
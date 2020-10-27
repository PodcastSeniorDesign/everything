package me.rooshi.podcastapp.feature.favoritegenre

import android.view.ViewGroup
import me.rooshi.podcastapp.common.base.MyAdapter
import me.rooshi.podcastapp.common.base.MyViewHolder
import me.rooshi.podcastapp.databinding.GenreItemBinding
import javax.inject.Inject

class GenreAdapter @Inject constructor(

) : MyAdapter<GenreItem, GenreItemBinding>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder<GenreItemBinding> {
        return MyViewHolder(parent, GenreItemBinding::inflate).apply {  }
    }

    override fun onBindViewHolder(holder: MyViewHolder<GenreItemBinding>, position: Int) {
        val result = getItem(position)

        holder.binding.textView.text = result.genre.name
        holder.binding.imageView2.setImageResource(result.genre.drawable)
    }

}
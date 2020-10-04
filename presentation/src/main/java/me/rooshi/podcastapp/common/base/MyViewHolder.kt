package me.rooshi.podcastapp.common.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class MyViewHolder <T : ViewBinding>(val binding: T) : RecyclerView.ViewHolder(binding.root) {
    constructor(
            parent: ViewGroup,
            bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> T
    ) : this(bindingInflater(LayoutInflater.from(parent.context), parent, false))
}
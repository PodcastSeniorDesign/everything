package me.rooshi.podcastapp.feature.main.explore.recommendation

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.rooshi.domain.model.Podcast
import me.rooshi.podcastapp.common.base.MyAdapter
import me.rooshi.podcastapp.common.base.MyViewHolder
import me.rooshi.podcastapp.databinding.RecommendItemBinding

class RecommendAdapter constructor(
        private val recommendations: ArrayList<Podcast>
) : MyAdapter<RecommendItem, RecommendItemBinding>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder<RecommendItemBinding> {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MyViewHolder<RecommendItemBinding>, position: Int) {
        TODO("Not yet implemented")
    }

}
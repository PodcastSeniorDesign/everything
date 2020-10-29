package me.rooshi.podcastapp.feature.main.explore.recommendation

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.rooshi.domain.model.Podcast
import me.rooshi.podcastapp.common.Navigator
import me.rooshi.podcastapp.common.base.MyAdapter
import me.rooshi.podcastapp.common.base.MyViewHolder
import me.rooshi.podcastapp.databinding.RecommendItemBinding
import javax.inject.Inject

class RecommendAdapter @Inject constructor(
        private val navigator: Navigator
) : MyAdapter<List<RecommendItem>, RecommendItemBinding>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder<RecommendItemBinding> {
        return MyViewHolder(parent, RecommendItemBinding::inflate)
    }

    override fun onBindViewHolder(holder: MyViewHolder<RecommendItemBinding>, position: Int) {

    }

}
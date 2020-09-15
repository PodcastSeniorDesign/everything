package me.rooshi.podcastapp.feature.main.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.rooshi.podcastapp.R
import me.rooshi.podcastapp.common.base.MyFragment

//dont need @AndroidEntryPoint since MainActivity already does
class ExploreFragment constructor(
    //private val someObject: Object
) : MyFragment(R.layout.explore_fragment) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.explore_fragment, container, false)
    }
}
package me.rooshi.podcastapp.common.base

import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class MyFragment(fragmentLayoutId: Int) : Fragment(fragmentLayoutId) {

}
package me.rooshi.podcastapp.common.base

import androidx.lifecycle.ViewModel

abstract class MyViewModel<in View: MyView<State>, State>(initialState: State) : ViewModel() {

}
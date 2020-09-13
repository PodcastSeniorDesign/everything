package me.rooshi.podcastapp.feature.main

import me.rooshi.podcastapp.common.base.MyViewModel
import javax.inject.Inject

//The purpose of view models is to save
class MainViewModel @Inject constructor(

) : MyViewModel<MainView, MainState>(MainState())
package me.rooshi.podcastapp.injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import me.rooshi.podcastapp.feature.main.podcastInfo.PodcastInfoActivity

@InstallIn(ApplicationComponent::class)
@Module
object IntentModule  {

    @Provides
    fun provideIntentWithExtra(activity: PodcastInfoActivity): String {
        return activity.intent.getStringExtra("podcast") as String
    }

}
package me.rooshi.podcastapp.injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object AppModule {

    /*
    @Singleton
    @Provides
    fun provideSomeObject(): Object {
        return Object()
    }
    */


}
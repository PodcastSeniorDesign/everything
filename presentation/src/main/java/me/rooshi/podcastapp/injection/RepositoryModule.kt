package me.rooshi.podcastapp.injection

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import me.rooshi.data.repository.PlayerRepositoryImpl
import me.rooshi.data.repository.PodcastRepositoryImpl
import me.rooshi.data.repository.UserRepositoryImpl
import me.rooshi.domain.repository.PlayerRepository
import me.rooshi.domain.repository.PodcastRepository
import me.rooshi.domain.repository.UserRepository
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
abstract class RepositoryModule  {

    @Binds
    @Singleton
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl) : UserRepository

    @Binds
    @Singleton
    abstract fun bindPlayerRepository(playerRepositoryImpl: PlayerRepositoryImpl) : PlayerRepository

    @Binds
    @Singleton
    abstract fun bindPodcastRepository(podcastRepositoryImpl: PodcastRepositoryImpl) : PodcastRepository
}
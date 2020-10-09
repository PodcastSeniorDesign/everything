package me.rooshi.domain.interactor

import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.core.Flowable
import me.rooshi.domain.repository.UserRepository
import javax.inject.Inject

class LogInUserEmail @Inject constructor(
        private val userRepository: UserRepository
) : Interactor<List<String>>() {

    override fun buildObservable(params: List<String>): Flowable<*> {
        return Flowable.just(params)
                .doOnNext { userRepository.logInUserEmail(params) }
    }
}
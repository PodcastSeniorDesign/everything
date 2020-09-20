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
                //.filter { params[0].isNotBlank() and params[1].isNotBlank() }
                .doOnNext { userRepository.logInUserEmail(params) }
    }
}
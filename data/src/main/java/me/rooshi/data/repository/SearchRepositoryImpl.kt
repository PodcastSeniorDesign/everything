package me.rooshi.data.repository

import android.util.Log
import com.google.firebase.functions.FirebaseFunctions
import io.reactivex.rxjava3.core.Observable
import me.rooshi.domain.model.Podcast
import me.rooshi.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
        private val firebaseFunctions: FirebaseFunctions
) : SearchRepository {

    companion object {
        val searchURL = "https://us-central1-podcast-app-32d03.cloudfunctions.net/podcasts-search"
    }

    override fun searchPodcasts(query: String): Observable<List<Podcast>> {
        val data = hashMapOf(
                "query" to query
        )
        return Observable.create { emitter ->
            firebaseFunctions.getHttpsCallable("podcasts-search")
                    .call(query)
                    .addOnCompleteListener { task ->
                        //val result = task.result?.data as String
                        Log.e("podcasts-search", task.result?.data.toString())
                        emitter.onNext(listOf(Podcast(name = "hardcoded")))
                        //emitter.onNext(parseSearchPodcastToListPodcast(result))
                    }
                    .addOnFailureListener {
                        Log.e("podcasts-search", it.message?: "")
                        emitter.onNext(listOf(Podcast(name = "failed")))
                    }
        }
    }

    private fun parseSearchPodcastToListPodcast(result: String) : List<Podcast> {
        return listOf(Podcast(name = "rooshi"), Podcast(name = "is the best"))
    }

}
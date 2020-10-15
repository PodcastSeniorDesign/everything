package me.rooshi.data.repository

import android.util.Log
import com.google.firebase.functions.FirebaseFunctions
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import io.reactivex.rxjava3.core.Observable
import me.rooshi.domain.model.Podcast
import me.rooshi.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
        private val firebaseFunctions: FirebaseFunctions,
        private val moshi: Moshi
) : SearchRepository {

    companion object {
        //***EPISODE KEYS
        const val imageURLKey: String = "image" //String
        const val thumbnailURLKey: String = "thumbnail" //String
        const val descriptionKey: String = "description_original" //String
        //const val itunesIDKey: String = "itunes_id" //Int
        //const val explicitKey: String = "explicit_content" //Boolean
        const val originalSiteURLKey: String = "link" //String
        const val lengthKey: String = "audio_length_sec" //Int
        const val listenNotesURLKey: String = "listennotes_url" //String
        const val titleKey: String = "title_original" //String
        //const val titleHighlightedKey: String = "title_highlighted" //String w/ HTML/xml tags
        const val rssURLKey: String = "rss" //String
        //const val descriptionHighlightedKey: String = "description_highlighted" //String w/HTML/xml tags
        //const val podcastKey: String = "podcast" //Hashmap with a lot of stuff
        const val idKey: String = "id" //String
        const val audioURLKey: String = "audio" //String
        const val publishedDateKey: String = "pub_date_ms" //Long
        //const val transcriptsKey: String = "transcripts_highlighted" //ArrayList

        //***PODCAST KEYS
        const val websiteKey: String = "website"
        const val publisherKey: String = "publisher_original"
        //const val earliestPubDateKey: String = "earliest_pub_date_ms"
        const val totalEpisodesKey: String = "total_episodes"
    }

    override fun searchPodcasts(query: String): Observable<List<Podcast>> {
        return Observable.create { emitter ->
            firebaseFunctions.getHttpsCallable("podcasts-search")
                    .call(query)
                    .addOnSuccessListener { task ->
                        val result = task.data as HashMap<*,*>
                        val list = parseSearchPodcastToListPodcast(result)
                        emitter.onNext(list)
                    }
                    .addOnFailureListener {
                        Log.e("podcasts-search error", it.message?: "")
                        emitter.onNext(listOf(Podcast(title = "failed")))
                    }
        }
    }

    override fun topPodcastsByGenre(): Observable<List<List<Podcast>>> {
        return Observable.create { emitter ->
            firebaseFunctions.getHttpsCallable("podcasts-getTopByGenre")
                    .call()
                    .addOnSuccessListener { task ->
                        val result = task.data as ArrayList<*>
                        //val list = listOf(parseSearchPodcastToListPodcast(result))
                        emitter.onNext(listOf(listOf(Podcast(title = "failed"))))
                    }
                    .addOnFailureListener {
                        Log.e("podcasts-topByGen error", it.message?: "")
                        emitter.onNext(listOf(listOf(Podcast(title = "failed"))))
                    }
        }
    }

    private fun parseSearchPodcastToListPodcast(result: HashMap<*,*>) : List<Podcast> {
        val list = result["result"] as ArrayList<*>
        val outList = mutableListOf<Podcast>()
        for (podcast in list) {
            val p = Podcast()
            val map = podcast as HashMap<*, *>
            p.imageURL = map[imageURLKey] as String
            p.thumbnailURL = map[thumbnailURLKey] as String
            p.description = map[descriptionKey] as String
            p.totalEpisodes = map[totalEpisodesKey] as Int
            p.websiteURL = map[websiteKey] as String
            p.title = map[titleKey] as String
            p.publisher = map[publisherKey] as String
            p.id = map[idKey] as String

            outList.add(p)
        }
        return outList
    }

}
package me.rooshi.data.repository

import android.util.Log
import com.google.firebase.functions.FirebaseFunctions
import com.squareup.moshi.Moshi
import io.reactivex.rxjava3.core.Observable
import me.rooshi.domain.model.Episode
import me.rooshi.domain.model.Podcast
import me.rooshi.domain.model.PodcastInfoResult
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
        const val episodeTitleKey: String = "title"
        const val episodeDescriptionKey: String = "description"
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
                        val result = task.data as ArrayList<*>
                        val list = parseSearchPodcastToList(result)
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

    private fun parseSearchPodcastToList(result: ArrayList<*>) : List<Podcast> {
//        val list = result["results"] as ArrayList<*>
        print(result)
        val outList = mutableListOf<Podcast>()
        for (podcast in result) {
            val p = Podcast()
            val map = podcast as HashMap<*, *>
            p.imageURL = map[imageURLKey] .toString()
            p.thumbnailURL = map[thumbnailURLKey] .toString()
            p.description = map[descriptionKey] .toString()
            p.totalEpisodes = map[totalEpisodesKey] as Int
//            p.websiteURL = map[websiteKey] .toString()
            p.title = map[titleKey] .toString()
            p.publisher = map[publisherKey] .toString()
            p.id = map[idKey] .toString()

            outList.add(p)
        }
        return outList
    }

    override fun getListOfEpisodes(podcast: Podcast, next: Long): Observable<PodcastInfoResult> {
        return Observable.create { emitter ->
            if (podcast.id.isEmpty()) emitter.onNext(PodcastInfoResult())

            val params = hashMapOf(
                    "id" to podcast.id,
                    "next" to next
            )

            firebaseFunctions.getHttpsCallable("podcasts-getEpisodes")
                    .call(params)
                    .addOnSuccessListener { task ->
                        val result = task.data as HashMap<*,*>
                        val next = result["next"] as? Long
                        val list = parseGetEpisodesToList(result["episodes"] as ArrayList<*>)
                        val ret = PodcastInfoResult(episodeList = list, nextCallInfo = next?: 0)
                        emitter.onNext(ret)
                    }
                    .addOnFailureListener{
                        Log.e("podcasts-getEpisodes", it.localizedMessage.toString())
                    }
        }
    }

    private fun parseGetEpisodesToList(list: ArrayList<*>) : List<Episode> {
        val outList = mutableListOf<Episode>()
        for (episode in list) {
            val e = Episode()
            val map = episode as HashMap<*, *>
            e.imageURL = map[imageURLKey].toString()
            e.thumbnailURL = map[thumbnailURLKey] .toString()

            //need to parse for html
            e.description = map[episodeDescriptionKey].toString()

//            p.websiteURL = map[websiteKey] .toString()
            e.title = map[episodeTitleKey].toString()
            e.id = map[idKey].toString()
            e.length = map[lengthKey] as Int
            e.audioURL = map[audioURLKey].toString()
            e.dateMilli = map[publishedDateKey] as Long

            outList.add(e)
        }
        return outList
    }

}
package me.rooshi.domain.repository

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import me.rooshi.domain.model.Post

interface PostsRepository {

    /*
    Observable: emit a stream elements (endlessly)
    Flowable: emit a stream of elements (endlessly, with backpressure)
                backpressure is when incoming data is coming faster than you can process it
    Single: emits exactly one element
    Maybe: emits zero or one elements
    Completable: emits a “complete” event, without emitting any data type, just a success/failure
     */

    //i think this returns observable because ??
    fun loadPosts() : Observable<List<Post>>

    fun loadPost(postId: String) : Single<Post>

    fun createPost(post: Post)

    fun deletePost(postId: String)

    //completable means we don't need any info back
    fun likePost(postId: String) : Completable
    fun unlikePost(postId: String) : Completable
}
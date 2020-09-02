package me.rooshi.domain.repository

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import me.rooshi.domain.model.Post

interface PostsRepository {

    //i'm honestly not sure if these are the kinds of functions that should be in here

    //i think this returns observable because ??
    fun loadPosts() : Observable<List<Post>>

    fun loadPost(postId: String)

    fun createPost(post: Post)

    fun deletePost(postId: String)

    //completable means we don't need any info back
    fun likePost(postId: String) : Completable
    fun unlikePost(postId: String) : Completable
}
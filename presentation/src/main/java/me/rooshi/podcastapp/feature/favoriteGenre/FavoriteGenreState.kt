package me.rooshi.podcastapp.feature.favoriteGenre

import me.rooshi.domain.model.Genre
import me.rooshi.podcastapp.R

data class FavoriteGenreState(
        //TODO move to like a real storage class
        val genres: List<FavoriteGenreItem> = listOf(
                FavoriteGenreItem(Genre("Arts", 100, R.drawable.taz)),
                FavoriteGenreItem(Genre("Business", 93, R.drawable.taz)),
                FavoriteGenreItem(Genre("Comedy", 133, R.drawable.taz)),
                FavoriteGenreItem(Genre("Education", 111, R.drawable.taz)),
                FavoriteGenreItem(Genre("Fiction", 168, R.drawable.taz)),
                FavoriteGenreItem(Genre("Government", 117, R.drawable.taz)),
                FavoriteGenreItem(Genre("Health & Fitness", 88, R.drawable.taz)),
                FavoriteGenreItem(Genre("History", 125, R.drawable.taz)),
                FavoriteGenreItem(Genre("Kids & Family", 132, R.drawable.taz)),
                FavoriteGenreItem(Genre("Leisure", 82, R.drawable.taz)),
                FavoriteGenreItem(Genre("Locally Focused", 151, R.drawable.taz)),
                FavoriteGenreItem(Genre("Music", 134, R.drawable.taz)),
                FavoriteGenreItem(Genre("News", 99, R.drawable.taz)),
                FavoriteGenreItem(Genre("Personal Finance", 144, R.drawable.taz)),
                FavoriteGenreItem(Genre("Religion & Spirituality", 69, R.drawable.taz)),
                FavoriteGenreItem(Genre("Science", 107, R.drawable.taz)),
                FavoriteGenreItem(Genre("Society & Culture", 122, R.drawable.taz)),
                FavoriteGenreItem(Genre("Sports", 77, R.drawable.taz)),
                FavoriteGenreItem(Genre("TV & Film", 68, R.drawable.taz)),
                FavoriteGenreItem(Genre("Technology", 127, R.drawable.taz)),
                FavoriteGenreItem(Genre("True Crime", 135, R.drawable.taz))
        ),
        val finished: Boolean = false
)
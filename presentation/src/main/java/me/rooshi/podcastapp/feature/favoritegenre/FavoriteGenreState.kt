package me.rooshi.podcastapp.feature.favoritegenre

import me.rooshi.domain.model.Genre
import me.rooshi.podcastapp.R

data class FavoriteGenreState(
        //TODO move to like a real storage class
        val genres: List<GenreItem> = listOf(
                GenreItem(Genre("Arts", 100, R.drawable.taz)),
                GenreItem(Genre("Business", 93, R.drawable.taz)),
                GenreItem(Genre("Comedy", 133, R.drawable.taz)),
                GenreItem(Genre("Education", 111, R.drawable.taz)),
                GenreItem(Genre("Fiction", 168, R.drawable.taz)),
                GenreItem(Genre("Government", 117, R.drawable.taz)),
                GenreItem(Genre("Health & Fitness", 88, R.drawable.taz)),
                GenreItem(Genre("History", 125, R.drawable.taz)),
                GenreItem(Genre("Kids & Family", 132, R.drawable.taz)),
                GenreItem(Genre("Leisure", 82, R.drawable.taz)),
                GenreItem(Genre("Locally Focused", 151, R.drawable.taz)),
                GenreItem(Genre("Music", 134, R.drawable.taz)),
                GenreItem(Genre("News", 99, R.drawable.taz)),
                GenreItem(Genre("Personal Finance", 144, R.drawable.taz)),
                GenreItem(Genre("Religion & Spirituality", 69, R.drawable.taz)),
                GenreItem(Genre("Science", 107, R.drawable.taz)),
                GenreItem(Genre("Society & Culture", 122, R.drawable.taz)),
                GenreItem(Genre("Sports", 77, R.drawable.taz)),
                GenreItem(Genre("TV & Film", 68, R.drawable.taz)),
                GenreItem(Genre("Technology", 127, R.drawable.taz)),
                GenreItem(Genre("True Crime", 135, R.drawable.taz))

        )
)
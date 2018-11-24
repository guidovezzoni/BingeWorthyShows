package com.guidovezzoni.bingeworthyshows.common.utils

import com.guidovezzoni.bingeworthyshows.common.model.datalayer.Result
import com.guidovezzoni.bingeworthyshows.common.model.presentationlayer.MovieDbSettings
import com.guidovezzoni.bingeworthyshows.common.model.presentationlayer.TvShow

private val SIZE_FAVOURITE = "w780"
private val SIZE_ORIGINAL = "original"

fun createFromResult(result: Result): TvShow {
    return TvShow(result.name,
            result.overview,
            result.posterPath,
            result.voteAverage)
}

/**
 * Returns the appropriate poster size. The favourite is preferred if present, otherwise the highest
 * resolution and failing that the original - we are assuming original is always present
 */
private fun returnBestPosterSize(movieDbSettings: MovieDbSettings): String {
    return when {
        movieDbSettings.posterSizes.contains(SIZE_FAVOURITE) -> SIZE_FAVOURITE
        movieDbSettings.posterSizes.size > 1 -> movieDbSettings.posterSizes[movieDbSettings.posterSizes.size - 2]
        else -> SIZE_ORIGINAL
    }
}


fun createWithSettings(tvShow: TvShow, movieDbSettings: MovieDbSettings): TvShow {
    val posterPath = movieDbSettings.imageBaseUrl + returnBestPosterSize(movieDbSettings) + tvShow.poster
    return tvShow.copy(poster = posterPath)
}

package com.guidovezzoni.bingeworthyshows.common.utils

import com.guidovezzoni.bingeworthyshows.common.model.datalayer.Result
import com.guidovezzoni.bingeworthyshows.common.model.presentationlayer.MdbSettings
import com.guidovezzoni.bingeworthyshows.common.model.presentationlayer.TvShow
import java.util.*

private const val AVERAGE_VOTE_FORMAT = "%.01f"

private const val SIZE_FAVOURITE = "w780"
private const val SIZE_ORIGINAL = "original"
private const val AVERAGE_VOTE_MISSING = "-"

fun createFromResult(result: Result): TvShow {
    return TvShow(result.name,
            result.originalName,
            result.overview,
            result.posterPath ?: "",
            result.backdropPath ?: "",
            result.voteAverage)
}

/**
 * Returns the appropriate poster size. The favourite is preferred if present, otherwise the highest
 * resolution and failing that the original - we are assuming original is always present
 */
private fun returnBestPosterSize(mdbSettings: MdbSettings): String {
    return when {
        mdbSettings.posterSizes.contains(SIZE_FAVOURITE) -> SIZE_FAVOURITE
        mdbSettings.posterSizes.size > 1 -> mdbSettings.posterSizes[mdbSettings.posterSizes.size - 2]
        else -> SIZE_ORIGINAL
    }
}

private fun returnBestBackDropSize(mdbSettings: MdbSettings): String {
    return when {
        mdbSettings.backdropSizes.contains(SIZE_FAVOURITE) -> SIZE_FAVOURITE
        mdbSettings.backdropSizes.size > 1 -> mdbSettings.backdropSizes[mdbSettings.backdropSizes.size - 2]
        else -> SIZE_ORIGINAL
    }
}


fun createWithSettings(tvShow: TvShow, mdbSettings: MdbSettings): TvShow {
    val newPosterPath = mdbSettings.imageBaseUrl + returnBestPosterSize(mdbSettings) + tvShow.posterPath
    val newBackDropPath = mdbSettings.imageBaseUrl + returnBestBackDropSize(mdbSettings) + tvShow.backDropPath
    return tvShow.copy(posterPath = newPosterPath, backDropPath = newBackDropPath)
}

fun getRating(tvShow: TvShow): String {
    val averageVote = tvShow.averageVote
    return if (averageVote != null) {
        String.format(Locale.getDefault(), AVERAGE_VOTE_FORMAT, averageVote)
    } else {
        AVERAGE_VOTE_MISSING
    }
}

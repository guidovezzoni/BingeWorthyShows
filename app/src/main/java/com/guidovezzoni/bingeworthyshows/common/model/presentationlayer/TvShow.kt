package com.guidovezzoni.bingeworthyshows.common.model.presentationlayer

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvShow(val title: String, val overview: String, val poster: String, val averageVote: Float?) : Parcelable

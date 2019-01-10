package com.guidovezzoni.bingeworthyshows.common.model.presentationlayer

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvShow(val name: String,
                  val originalName: String,
                  val overview: String,
                  val posterPath: String,
                  val backDropPath: String,
                  val averageVote: Float?) : Parcelable

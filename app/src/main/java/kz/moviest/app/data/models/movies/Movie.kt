package kz.moviest.app.data.models.movies

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val vote_average: Float?,
    val title: String?,
    val overview: String?,
    val poster_path: String?,
    val release_date: String?,
    val backdrop_path: String?, // show in description
    val popularity: Float?, // show in description
    val tagline: String? // show in description
): Parcelable
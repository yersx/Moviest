package kz.moviest.app.data.models.movies

import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.android.parcel.Parcelize
import kz.moviest.app.R

@Parcelize
data class Movies(
    val page: Int,
    val total_pages: Int,
    val results: List<Movie>?
) : Parcelable
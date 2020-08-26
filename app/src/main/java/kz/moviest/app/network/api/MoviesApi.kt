package kz.moviest.app.network.api

import kz.moviest.app.data.models.movies.Movie
import kz.moviest.app.data.models.movies.Movies
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface MoviesApi {

    @GET("popular")
    fun getMovies(@QueryMap params: @JvmSuppressWildcards Map<String, Any>): Call<Movies>

    @JvmSuppressWildcards
    @GET("popular")
    suspend fun getMoviesFiltered(@QueryMap params: Map<String, Any>): Response<Movies>

    @GET("{id}")
    suspend fun getMovieDetail(@Path("id") id: Int): Response<Movie>
}
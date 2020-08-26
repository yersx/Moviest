package kz.moviest.app.data.repository.movies

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import kz.moviest.app.data.models.movies.Movie
import kz.moviest.app.data.models.movies.Movies
import kz.moviest.app.data.models.network.Resource
import kz.moviest.app.utils.live_data.Event

interface MovieRepository {

    fun getMovies(params: Map<String, Any>): LiveData<PagedList<Movie>>

    fun getMoviesInitialResource(): LiveData<Resource<Int>>

    fun getMoviesAfterResource(): LiveData<Resource<Unit>>

    fun retryMoviesAfter()

    fun getMoviesFiltered(params: Map<String, Any>): LiveData<Resource<Movies>>

    fun getMovieDetail(id: Int):  LiveData<Event<Resource<Movie>>>
}
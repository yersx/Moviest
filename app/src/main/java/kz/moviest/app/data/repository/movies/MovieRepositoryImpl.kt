package kz.moviest.app.data.repository.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import kz.moviest.app.data.models.movies.Movie
import kz.moviest.app.data.models.movies.Movies
import kz.moviest.app.data.models.network.Resource
import kz.moviest.app.data.preferences.Preferences
import kz.moviest.app.data.repository.error_handler.RepositoryErrorHandler
import kz.moviest.app.network.api.MoviesApi
import kz.moviest.app.utils.live_data.Event
import javax.inject.Inject

class MovieRepositoryImpl
@Inject constructor(
    private val api: MoviesApi,
    private val errorHandler: RepositoryErrorHandler,
    private val moviesDataSourceFactory: MoviesDataSourceFactory
) : MovieRepository {

    override fun getMoviesInitialResource(): LiveData<Resource<Int>> {
        return Transformations.switchMap(moviesDataSourceFactory.moviesDataSourceLiveData) {
            it.initialResource
        }
    }

    override fun getMoviesAfterResource(): LiveData<Resource<Unit>> {
        return Transformations.switchMap(moviesDataSourceFactory.moviesDataSourceLiveData) {
            it.afterResource
        }
    }

    override fun retryMoviesAfter() {
        moviesDataSourceFactory.moviesDataSourceLiveData.value?.retryAfter()
    }

    override fun getMovies(params: Map<String, Any>): LiveData<PagedList<Movie>> {

        moviesDataSourceFactory.setParams(params)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(20)
            .build()

        return LivePagedListBuilder(
            moviesDataSourceFactory,
            config
        ).build()
    }

    override fun getMoviesFiltered(params: Map<String, Any>): LiveData<Resource<Movies>> {
        return liveData {
            try {
                emit(
                    Resource.loading(null)
                )

                val request = api.getMoviesFiltered(params)
                with(request) {
                    if (isSuccessful) {
                        emit(
                            Resource.success(body())
                        )
                    } else {
                        emit(
                            errorHandler.getError(code(), errorBody())
                        )
                    }
                }
            } catch (e: Exception) {
                emit(
                    errorHandler.getError(e)
                )
            }
        }
    }


    override fun getMovieDetail(id: Int): LiveData<Event<Resource<Movie>>> {
        return liveData {
            try {
                emit(
                    Event(Resource.loading(null))
                )

                val request = api.getMovieDetail(id)
                with(request) {
                    if (isSuccessful) {
                        emit(
                            Event(
                                Resource.success(
                                    body()
                                )
                            )
                        )
                    } else {
                        emit(
                            Event(
                                errorHandler.getError(code(), errorBody())
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                emit(
                    Event(errorHandler.getError(e))
                )
            }
        }
    }


}
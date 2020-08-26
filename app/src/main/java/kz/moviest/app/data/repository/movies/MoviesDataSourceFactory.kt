package kz.moviest.app.data.repository.movies

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import kz.moviest.app.data.models.movies.Movie
import javax.inject.Inject

class MoviesDataSourceFactory
@Inject constructor(
    private val dataSource: MoviesDataSource
) : DataSource.Factory<Int, Movie>() {

    private var params: Map<String, Any>? = null


    fun setParams(params: Map<String, Any>) {
        this.params = params
        dataSource.setParams(params)
    }

    val moviesDataSourceLiveData = MutableLiveData<MoviesDataSource>()

    override fun create(): DataSource<Int, Movie> {
        moviesDataSourceLiveData.postValue(dataSource)
        return dataSource
    }

}
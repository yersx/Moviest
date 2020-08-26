package kz.moviest.app.data.repository.movies

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.moviest.app.data.consts.MoviesParams
import kz.moviest.app.data.models.movies.Movie
import kz.moviest.app.data.models.movies.Movies
import kz.moviest.app.data.models.network.Resource
import kz.moviest.app.data.repository.error_handler.RepositoryErrorHandler
import kz.moviest.app.network.api.MoviesApi
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class MoviesDataSource
@Inject constructor(
    private val api: MoviesApi,
    private val errorHandler: RepositoryErrorHandler
) : PageKeyedDataSource<Int, Movie>() {

    companion object {
        const val firstPage = 1
    }

    private var params: Map<String, Any>? = null

    fun setParams(params: Map<String, Any>) {
        this.params = params
    }

    val initialResource = MutableLiveData<Resource<Int>>()
    val afterResource = MutableLiveData<Resource<Unit>>()

    override fun loadInitial(
        loadInitialParams: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        val movieParams = mutableMapOf<String, Any>()
        movieParams[MoviesParams.PAGE] = "1"

        params?.let {
            movieParams.putAll(it)
        }
        try {
            val call: Call<Movies> = api.getMovies(movieParams)
            initialResource.postValue(Resource.loading(null))
            val result: Response<Movies> = call.execute()
            if (result.isSuccessful) {
                result.body()?.let { res: Movies ->
                    callback.onResult(res.results!!, null, firstPage + 1)
                    initialResource.postValue(Resource.success(res.results.size))
                }
            } else {
                initialResource.postValue(
                    errorHandler.getError(
                        result.code(),
                        result.errorBody()
                    )
                )
            }
        } catch (e: Exception) {
            initialResource.postValue(errorHandler.getError(e))
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        //do nothing
    }

    private var paramsAfter: LoadParams<Int>? = null
    private var callbackAfter: LoadCallback<Int, Movie>? = null

    override fun loadAfter(loadParams: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        val movieParams = mutableMapOf<String, Any>()
        movieParams[MoviesParams.PAGE] = (loadParams.key).toString()

        params?.let {
            movieParams.putAll(it)
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call: Call<Movies> = api.getMovies(
                        movieParams
                    )
                afterResource.postValue(Resource.loading(null))
                val result: Response<Movies> = call.execute()
                if (result.isSuccessful) {
                    result.body()?.let { res: Movies ->
                        callback.onResult(res.results!!, loadParams.key + 1)
                        afterResource.postValue(Resource.success(null))
                    }
                } else {
                    paramsAfter = loadParams
                    callbackAfter = callback
                    afterResource.postValue(
                        errorHandler.getError(
                            result.code(),
                            result.errorBody()
                        )
                    )
                }
            } catch (e: Exception) {
                paramsAfter = loadParams
                callbackAfter = callback
                afterResource.postValue(errorHandler.getError(e))
            }
        }
    }

    fun retryAfter() {
        if (paramsAfter != null
            && callbackAfter != null
        ) {
            loadAfter(paramsAfter!!, callbackAfter!!)

            paramsAfter = null
            callbackAfter = null
        }
    }
}
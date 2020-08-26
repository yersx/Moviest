package kz.moviest.app.ui.movies.movie

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kz.moviest.app.data.models.movies.Movie
import kz.moviest.app.data.models.network.Resource
import kz.moviest.app.data.models.network.Status
import kz.moviest.app.data.repository.movies.MovieRepository
import kz.moviest.app.ui_common.callbacks.RetryCallback
import kz.moviest.app.utils.Logger
import javax.inject.Inject

class MovieDetailViewModel
@Inject constructor(
    private val app: Application,
    private val repository: MovieRepository
) : AndroidViewModel(app) {

    private var id: Int? = null

    private val _refresh = MutableLiveData<Int>()

    fun setArgs(id: Int) {
        this.id = id
        _refresh.postValue(id)

    }

    val retryCallback = object : RetryCallback {
        override fun onRetryClick() {
            _refresh.value = id
        }
    }

    /**
     * Network
     */
    val movieDetailResource =
        Transformations.switchMap(_refresh) {
            repository.getMovieDetail(it)
        }

    val resource: LiveData<Resource<Any>>?
        get() = Transformations.map(movieDetailResource) {
            it.peekContent()
        }

    val status: LiveData<Status>?
        get() = Transformations.map(movieDetailResource) {
            it.peekContent().status
        }

    val imageFieldText = MutableLiveData<String>()
    val titleFieldText = MutableLiveData<String>()
    val taglineFieldText = MutableLiveData<String>()
    val overviewFieldText = MutableLiveData<String>()
    val releaseDateFieldText = MutableLiveData<String>()
    val popularityDateFieldText = MutableLiveData<String>()
    val voteAverageFieldText = MutableLiveData<String>()


    fun onMovieDetailSuccess(movie: Movie?){
        movie?.apply {
            imageFieldText.postValue(backdrop_path)
            titleFieldText.postValue(title)
            taglineFieldText.postValue(tagline)
            overviewFieldText.postValue(overview)
            releaseDateFieldText.postValue(release_date)
            popularityDateFieldText.postValue(popularity.toString())
            voteAverageFieldText.postValue(vote_average.toString())
        }
    }
}
package kz.moviest.app.ui.movies

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import kz.moviest.app.data.models.movies.Movie
import kz.moviest.app.data.models.network.Resource
import kz.moviest.app.data.models.network.Status
import kz.moviest.app.data.repository.movies.MovieRepository
import kz.moviest.app.ui_common.callbacks.RecyclerViewItemClickCallback
import kz.moviest.app.ui_common.callbacks.RetryCallback
import kz.moviest.app.ui_common.callbacks.SwipeToRefreshCallback
import kz.moviest.app.utils.Logger
import kz.moviest.app.utils.live_data.Event
import javax.inject.Inject

class MoviesViewModel
@Inject constructor(
    private val app: Application,
    private val repository: MovieRepository
) : AndroidViewModel(app) {


    val moviesInitialResource = repository.getMoviesInitialResource()
    val moviesAfterResource = repository.getMoviesAfterResource()
    var filter: Map<String, Any>? = null

    val status: LiveData<Status>?
        get() = Transformations.map(moviesInitialResource) {
            it.status
        }

    val resource: LiveData<Resource<Any>>?
        get() = Transformations.map(moviesInitialResource) {
            it
        }

    var isRefreshing = MutableLiveData<Boolean>()
    private val _refresh = MutableLiveData<Unit>()

    init {
        isRefreshing.value = false
        _refresh.postValue(Unit)
    }

    val retryCallback = object : RetryCallback {
        override fun onRetryClick() {
            _refresh.postValue(Unit)
        }
    }

    val afterRetryCallback = object : RetryCallback {
        override fun onRetryClick() {
            repository.retryMoviesAfter()
        }
    }

    /**
     *
     */
    val swipeToRefreshCallback = object :
        SwipeToRefreshCallback {
        override fun onSwipeToRefresh() {
            isRefreshing.value = false
            _refresh.postValue(Unit)
        }
    }

    /**
     * Network
     */

    val moviesPagedList =
        Transformations.switchMap(_refresh) {
            var params = mutableMapOf<String, Any>()
            filter?.let {
                params.putAll(it)
            }
            repository.getMovies(params)
        }

    val emptyFieldVisible = MutableLiveData<Boolean>()

    fun onMoviesInitialResourceSuccess(count: Int?) {
        if (count != null
            && count > 0
        ) {
            emptyFieldVisible.postValue(false)
        } else {
            emptyFieldVisible.postValue(true)
        }
    }

    /**
     * Content
     */
    private val _list = MutableLiveData<PagedList<Movie>>()
    val list: LiveData<PagedList<Movie>>
        get() = _list


    fun onMoviesResourceSuccess(products: PagedList<Movie>) {
        _list.postValue(products)
    }
    /**
     *
     */
    private val _openFilter = MutableLiveData<Event<Unit>>()
    val openFilter: LiveData<Event<Unit>>
        get() = _openFilter

    fun onFilterItemClick() {
        _openFilter.postValue(Event(Unit))
    }

    private val _openMovie = MutableLiveData<Event<Int>>()
    val openMovie: LiveData<Event<Int>>
        get() = _openMovie


    val recyclerViewItemClickCallback = object :
        RecyclerViewItemClickCallback {
        override fun onRecyclerViewItemClick(any: Any) {
            if (any is Movie) {
                _openMovie.postValue(Event(any.id))
            }
        }
    }


    fun onApplyBtnClick(filter: Map<String, Any>?){
        this.filter = filter
        Logger.e("filter: $filter")
        _refresh.postValue(Unit)

    }
}
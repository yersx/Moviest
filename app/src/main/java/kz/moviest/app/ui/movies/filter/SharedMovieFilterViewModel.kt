package kz.moviest.app.ui.movies.filter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kz.moviest.app.utils.live_data.Event
import javax.inject.Inject

class SharedMovieFilterViewModel
@Inject constructor(
    private val app: Application
) : AndroidViewModel(app) {

    var filter: Map<String, Any>? = null

    private val _applyBtnClick = MutableLiveData<Event<Map<String, Any>? >>()
    val applyBtnClick: LiveData<Event<Map<String, Any>? >>
        get() = _applyBtnClick

    val callback = object : MovieFilterCallback {
        override fun onApplyBtnClick(filter: Map<String, Any>?) {
            this@SharedMovieFilterViewModel.filter = filter

            _applyBtnClick.postValue(Event(filter))
        }

    }

}
package kz.moviest.app.ui.movies.filter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kz.moviest.app.data.consts.FilterParams
import kz.moviest.app.utils.ToastUtils
import kz.moviest.app.utils.live_data.Event
import javax.inject.Inject

class MovieFilterViewModel
@Inject constructor(
    private val app: Application
) : AndroidViewModel(app) {

    private lateinit var callback: MovieFilterCallback

    val votingFromFieldText = MutableLiveData<String>()
    val votingToFieldText = MutableLiveData<String>()
    val yearFieldText = MutableLiveData<String>()

    fun setArgs(
        callback: MovieFilterCallback
    ) {
        this.callback = callback
    }

    init {
        votingFromFieldText.postValue("1")
        votingToFieldText.postValue("10")
        yearFieldText.postValue("2020")
    }

    fun onApplyBtnClick(){
        val params = mutableMapOf<String, Any>()

        if(!votingFromFieldText.value.isNullOrEmpty() && !votingToFieldText.value.isNullOrEmpty()){

            if(votingFromFieldText.value!!.toFloat() < votingToFieldText.value!!.toFloat()){
                yearFieldText.value?.let {
                    params[FilterParams.YEAR] = it
                }
                votingToFieldText.value?.let {
                    params[FilterParams.VOTE_LESS_THAN] = it
                }
                votingFromFieldText.value?.let {
                    params[FilterParams.VOTE_GREATER_THAN] = it
                }

                callback.onApplyBtnClick(
                    params
                )

                _popBackStack.postValue(Event(Unit))
            } else {
                ToastUtils.show(getApplication(), "Please choose correct values")
            }
        }
    }

    private val _popBackStack = MutableLiveData<Event<Unit>>()
    val popBackStack: LiveData<Event<Unit>>
        get() = _popBackStack
}

package kz.moviest.app.ui_common.views.date

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kz.moviest.app.R
import kz.moviest.app.data.enums.app.DatePatternEnums
import kz.moviest.app.utils.convertDateToString
import kz.moviest.app.utils.live_data.Event
import java.util.*
import javax.inject.Inject

class DateViewModel
@Inject constructor(
    private val app: Application
) : AndroidViewModel(app) {

    /**
     * Fields
     */
    val titleFieldText = MutableLiveData<String>()
    val valueFieldText = MutableLiveData<String>()
    val notChosenErrorVisibility = MutableLiveData<Int>()

    /**
     * Init
     */
    init {
        valueFieldText.postValue(app.getString(R.string.field_select_value))

        notChosenErrorVisibility.postValue(View.GONE)
    }

    /**
     *  Select
     */
    private val _openDialog = MutableLiveData<Event<Unit>>()
    val openDialog: LiveData<Event<Unit>>
        get() = _openDialog

    fun onCalendarBtnClick() {
        _openDialog.postValue(Event(Unit))
    }

    /**
     *
     */
    fun setTitle(title: String) {
        titleFieldText.postValue(title)
    }

    fun onSelected(value: String) {
        valueFieldText.postValue(value)
        notChosenErrorVisibility.postValue(View.GONE)
    }

    fun onSelected(value: Date) {
        valueFieldText.postValue(
            convertDateToString(
                value,
                DatePatternEnums.DDMMYYYY_BY_PERIOD.id
            )
        )
        notChosenErrorVisibility.postValue(View.GONE)
    }

    fun showNotSelectedError() {
        notChosenErrorVisibility.postValue(View.VISIBLE)
    }

}
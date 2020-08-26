package kz.moviest.app.ui.splash

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kz.moviest.app.utils.live_data.Event
import javax.inject.Inject

class SplashViewModel
@Inject constructor(
    private val app: Application
) : AndroidViewModel(app) {

    companion object {
        private const val MILLIS_IN_FUTURE = 3 * 1000L
        private const val INTERVAL = 1000L
    }

    init {
        startTimer()
    }

    private var countDownTimer: CountDownTimer? = null

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(
            MILLIS_IN_FUTURE,
            INTERVAL
        ) {
            override fun onFinish() {
                openNext()
            }

            override fun onTick(millisUntilFinished: Long) {
                //do nothing
            }
        }.start()
    }

    private fun openNext() {
        _openMain.postValue(Event(Unit))
    }

    private val _openMain = MutableLiveData<Event<Unit>>()
    val openMain: LiveData<Event<Unit>>
        get() = _openMain

    override fun onCleared() {
        countDownTimer?.cancel()
        super.onCleared()
    }

}
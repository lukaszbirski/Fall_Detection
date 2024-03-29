package pl.birski.falldetector.presentation.viewmodel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.birski.falldetector.R
import pl.birski.falldetector.components.interfaces.MessageSender
import pl.birski.falldetector.components.interfaces.Sensor
import pl.birski.falldetector.other.PrefUtil
import pl.birski.falldetector.repository.Repository
import pl.birski.falldetector.service.TrackingService
import pl.birski.falldetector.service.enum.ServiceActions
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LockScreenViewModel @Inject constructor(
    private val prefUtil: PrefUtil,
    private val messageSender: MessageSender,
    private val repository: Repository,
    private val sensor: Sensor
) : ViewModel() {

    private val _displayDialog = MutableLiveData<Boolean>()
    val displayDialog: LiveData<Boolean> get() = _displayDialog

    private var timerLengthSeconds = calculateTimeFromPrefs()
    private var secondsRemaining = calculateTimeFromPrefs()

    init {
        _displayDialog.postValue(false)
    }

    private fun calculateTimeFromPrefs() = prefUtil.getTimerLength() * 60

    fun getTimerLengthSeconds() = timerLengthSeconds

    fun getSecondsRemaining() = secondsRemaining

    fun countProgress() = (timerLengthSeconds - secondsRemaining).toInt()

    fun updateSecondsRemaining(millisUntilFinished: Long) {
        secondsRemaining = millisUntilFinished / 1000
    }

    fun getTextForCountdownTextView(): String {
        val minutes = secondsRemaining / 60
        val seconds = secondsRemaining - minutes * 60
        val secondsString = seconds.toString()
        return "$minutes:${if (secondsString.length == 2) secondsString else "0 $secondsString"}"
    }

    fun isSendingMessageAllowed() = prefUtil.isSendingMessageAllowed()

    fun manageCountDownFinished(context: Context) {
        Timber.d("Countdown finished")
        isSendingMessageAllowed().takeIf { it }
            ?.let { sendMessages(context) }

        _displayDialog.postValue(true)
    }

    private fun sendMessages(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.observeAllContacts()
            result.takeIf { it.isNotEmpty() }?.let {
                val array: Array<String> = it.map { contact ->
                    context.getString(
                        R.string.template_phone_number,
                        contact.prefix,
                        contact.number
                    )
                }.toTypedArray()
                messageSender.startSendMessages(array)
            }
            Timber.d("Messages was sent!")
        }
    }

    fun stopService(context: Context) = sendCommandToService(context, ServiceActions.STOP)
        .also { sensor.stopMeasurement() }

    private fun sendCommandToService(context: Context, action: ServiceActions) =
        Intent(context, TrackingService::class.java).also {
            it.action = action.name
            context.startService(it)
        }
}

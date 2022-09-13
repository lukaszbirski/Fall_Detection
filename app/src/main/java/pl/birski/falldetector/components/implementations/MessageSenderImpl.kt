package pl.birski.falldetector.components.implementations

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.telephony.SmsManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import pl.birski.falldetector.R
import pl.birski.falldetector.components.interfaces.LocationTracker
import pl.birski.falldetector.components.interfaces.MessageSender
import pl.birski.falldetector.other.Constants
import javax.inject.Inject

class MessageSenderImpl @Inject constructor(
    private val context: Context,
    private val locationTracker: LocationTracker
) : MessageSender {

    private var messageBody = ""

    private var mMessageSentParts = 0
    private var mMessageSentTotalParts = 0
    private var mMessageSentCount = 0

    private lateinit var messages: Array<String>

    override fun startSendMessages(messages: Array<String>) {
        this.messages = messages
        this.messageBody = getMessage()

        registerBroadCastReceivers()
        mMessageSentCount = 0
        sendSMS(messages[mMessageSentCount], messageBody)
    }

    private fun sendNextMessage() {
        if (thereAreSmsToSend()) {
            sendSMS(messages[mMessageSentCount], messageBody)
        } else {
            Toast.makeText(
                context,
                context.getString(R.string.message_sender_all_message_sent_text),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun thereAreSmsToSend(): Boolean {
        return mMessageSentCount < messages.size
    }

    private fun sendSMS(phoneNumber: String, message: String) {
        val sms: SmsManager? = context.getSystemService(SmsManager::class.java)
        val parts = sms?.divideMessage(message)
        parts?.let {
            mMessageSentTotalParts = it.size
        }

        val deliveryIntents = ArrayList<PendingIntent>()
        val sentIntents = ArrayList<PendingIntent>()

        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.FLAG_MUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }

        val sentPI = PendingIntent.getBroadcast(
            context,
            0,
            Intent(Constants.CUSTOM_FALL_DETECTED_INTENT_SMS_SENT),
            pendingIntent
        )
        val deliveredPI = PendingIntent.getBroadcast(
            context,
            0,
            Intent(Constants.CUSTOM_FALL_DETECTED_INTENT_SMS_DELIVERED),
            pendingIntent
        )
        for (j in 0 until mMessageSentTotalParts) {
            sentIntents.add(sentPI)
            deliveryIntents.add(deliveredPI)
        }
        mMessageSentParts = 0
        sms?.sendMultipartTextMessage(phoneNumber, null, parts, sentIntents, deliveryIntents)
    }

    private fun registerBroadCastReceivers() {
        context.registerReceiver(
            object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    when (resultCode) {
                        AppCompatActivity.RESULT_OK -> {
                            mMessageSentParts++
                            if (mMessageSentParts == mMessageSentTotalParts) {
                                mMessageSentCount++
                                sendNextMessage()
                            }
                            Toast.makeText(
                                context,
                                context?.getString(R.string.message_sender_message_sent_text),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        else -> {
                            Toast.makeText(
                                context,
                                context?.getString(R.string.message_sender_error_when_sending_text),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            },
            IntentFilter(Constants.CUSTOM_FALL_DETECTED_INTENT_SMS_SENT)
        )
        context.registerReceiver(
            object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    when (resultCode) {
                        AppCompatActivity.RESULT_OK -> Toast.makeText(
                            context,
                            context?.getString(R.string.message_sender_message_delivered_text),
                            Toast.LENGTH_SHORT
                        ).show()
                        AppCompatActivity.RESULT_CANCELED -> Toast.makeText(
                            context,
                            context?.getString(R.string.message_sender_message_not_delivered_text),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            },
            IntentFilter(Constants.CUSTOM_FALL_DETECTED_INTENT_SMS_DELIVERED)
        )
    }

    internal fun getMessage() = context.getString(
        R.string.message_sender_text_body,
        locationTracker.getAddress(),
        locationTracker.getLongitude().toString(),
        locationTracker.getLatitude().toString()
    )
}

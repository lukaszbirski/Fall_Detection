package com.example.android.architecture.blueprints.todoapp.other

object Constants {

    const val NOTIFICATION_CHANNEL_ID = "tracking_channel"
    const val NOTIFICATION_CHANNEL_NAME = "Tracking"
    const val NOTIFICATION_ID = 1

    // signal frequency is 50Hz and cut-off frequency is 0.25 Hz
    const val FREQUENCY_CUT_OFF = 0.25
    const val FREQUENCY_MEASUREMENT = 50.0

    const val INTERVAL_MILISEC = (1_000 / FREQUENCY_MEASUREMENT).toInt()

    private const val MIN_MAX_SLIDING_WINDOW_TIME_SEC = 0.1F // SW size for SVminmax is 0.1 s
    private const val POSTURE_DETECTION_SW_TIME_SEC = 0.4F // SW size for posture detection is 0.4 s

    const val MIN_MAX_SW_SIZE = MIN_MAX_SLIDING_WINDOW_TIME_SEC * 1_000 / INTERVAL_MILISEC
    const val POSTURE_DETECTION_SW_SIZE = POSTURE_DETECTION_SW_TIME_SEC * 1_000 / INTERVAL_MILISEC

    // after impact need to wait 2 s
    const val IMPACT_TIME_SPAN = 2_000 / INTERVAL_MILISEC

    // impact is measured within 1 s frame
    const val FALLING_TIME_SPAN = 1_000 / INTERVAL_MILISEC

    const val CUSTOM_FALL_DETECTED_RECEIVER = "om.example.android.architecture.blueprints.todoapp.CUSTOM_INTENT"

    const val CUSTOM_FALL_DETECTED_INTENT_SMS_SENT = "om.example.android.architecture.blueprints.todoapp.SMS_SENT"

    const val CUSTOM_FALL_DETECTED_INTENT_SMS_DELIVERED = "om.example.android.architecture.blueprints.todoapp.SMS_DELIVERED"
}

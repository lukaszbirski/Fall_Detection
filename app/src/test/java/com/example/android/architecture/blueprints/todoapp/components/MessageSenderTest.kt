package com.example.android.architecture.blueprints.todoapp.components

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.components.fake.LocationTrackerFake
import com.example.android.architecture.blueprints.todoapp.components.implementations.MessageSenderImpl
import com.example.android.architecture.blueprints.todoapp.components.interfaces.LocationTracker
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.internal.DoNotInstrument

/**
 * Unit tests for the implementation of [MessageSenderImpl].
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
@DoNotInstrument
class MessageSenderTest {

    // system in test
    private lateinit var messageSender: MessageSenderImpl

    private var locationTracker: LocationTracker = LocationTrackerFake()
    private var context: Context = ApplicationProvider.getApplicationContext()

    @Before
    fun setup() {
        messageSender = MessageSenderImpl(context, locationTracker)
    }

    @Test
    fun `check if sent message is correct`() {
        val message = context.getString(
            R.string.message_sender_text_body,
            locationTracker.getAddress(),
            locationTracker.getLongitude(),
            locationTracker.getLatitude()
        )

        val result = messageSender.getMessage()

        assertEquals(message, result)
    }
}

package pl.birski.falldetector

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import pl.birski.falldetector.other.Constants
import pl.birski.falldetector.presentation.LockScreenActivity

@AndroidEntryPoint
class HiltTestActivity : AppCompatActivity() {

    private var isFallDetected = false

    private var fallReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent) {
            intent.action.let {
                if (!isFallDetected) {
                    isFallDetected = true

                    Intent(context, LockScreenActivity::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        it.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
                        startActivity(it)
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBroadcastReceivers()
    }

    private fun registerBroadcastReceivers() {
        IntentFilter(Constants.CUSTOM_FALL_DETECTED_RECEIVER).also {
            registerReceiver(fallReceiver, it)
        }
    }
}

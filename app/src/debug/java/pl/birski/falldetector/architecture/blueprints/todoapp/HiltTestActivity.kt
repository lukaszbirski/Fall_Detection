package pl.birski.falldetector.architecture.blueprints.todoapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pl.birski.falldetector.architecture.blueprints.todoapp.other.Constants
import pl.birski.falldetector.architecture.blueprints.todoapp.presentation.LockScreenActivity
import dagger.hilt.android.AndroidEntryPoint

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

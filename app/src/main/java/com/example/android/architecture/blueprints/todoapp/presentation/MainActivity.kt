package com.example.android.architecture.blueprints.todoapp.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.databinding.LayoutMainBinding
import com.example.android.architecture.blueprints.todoapp.other.Constants
import com.example.android.architecture.blueprints.todoapp.presentation.fragment.ContactsFragment
import com.example.android.architecture.blueprints.todoapp.presentation.fragment.GraphFragment
import com.example.android.architecture.blueprints.todoapp.presentation.fragment.HomeFragment
import com.example.android.architecture.blueprints.todoapp.presentation.fragment.SettingsFragment
import com.example.android.architecture.blueprints.todoapp.presentation.listener.PassDataInterface
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), PassDataInterface {

    private lateinit var binding: LayoutMainBinding

    private var isFallDetected = false

    private var mMessageReceiver = object : BroadcastReceiver() {

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
        binding = LayoutMainBinding.inflate(layoutInflater)

        registerBroadcastReceiver()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment

        binding.bottomNav.setOnItemSelectedListener { menuItem ->
            manageNavigation(menuItem)
            true
        }

        navHostFragment.findNavController()
            .addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.settingsFragment,
                    R.id.graphFragment,
                    R.id.contactsFragment,
                    R.id.homeFragment -> binding.bottomNav.visibility = View.VISIBLE
                    else -> binding.bottomNav.visibility = View.GONE
                }
            }

        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterBroadcastReceiver()
    }

    override fun onDataReceived(isDetected: Boolean) {
        isFallDetected = isDetected
    }

    private fun unregisterBroadcastReceiver() {
        unregisterReceiver(mMessageReceiver)
    }

    private fun registerBroadcastReceiver() {
        IntentFilter(Constants.CUSTOM_FALL_DETECTED_RECEIVER).also {
            registerReceiver(mMessageReceiver, it)
        }
    }

    private fun manageNavigation(item: MenuItem) {
        when (item.itemId) {
            R.id.homeFragment -> navigateToFragment(HomeFragment())
            R.id.graphFragment -> navigateToFragment(GraphFragment())
            R.id.settingsFragment -> navigateToFragment(SettingsFragment())
            R.id.contactsFragment -> navigateToFragment(ContactsFragment())
        }
    }

    private fun navigateToFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_nav_host_fragment, fragment)
            commit()
        }
}

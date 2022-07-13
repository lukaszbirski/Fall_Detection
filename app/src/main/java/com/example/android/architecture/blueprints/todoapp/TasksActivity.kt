package com.example.android.architecture.blueprints.todoapp

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.android.architecture.blueprints.todoapp.databinding.LayoutMainBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class TasksActivity : AppCompatActivity() {

    private lateinit var binding: LayoutMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutMainBinding.inflate(layoutInflater)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

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
//                    R.id.graphFragment,
                    R.id.contactsFragment,
                    R.id.homeFragment -> binding.bottomNav.visibility = View.VISIBLE
                    else -> binding.bottomNav.visibility = View.GONE
                }
            }

        setContentView(binding.root)
    }

    private fun manageNavigation(item: MenuItem) {
        when (item.itemId) {
            R.id.homeFragment -> navigateToFragment(HomeFragment())
//            R.id.graphFragment -> navigateToFragment(GraphFragment())
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

package com.fitpeo.ui.activity

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.fitpeo.R
import com.fitpeo.databinding.ActivityMainBinding
import com.fitpeo.utilities.setStatusBarColor

class MainActivity : AppCompatActivity() {

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment).navController
    }

    private lateinit var appBarConfiguration: AppBarConfiguration

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this@MainActivity,
            R.layout.activity_main
        )

        setSupportActionBar(binding.toolbar)
        setStatusBarColor(getColor(R.color.colorApplicationBackground))


        /*
        * Attaches toolbar with navigation controller to keep toolbar synced between page load and navigation events.
        * Like back press or toolbar titles.
        * Appbar configuration
        * */
        appBarConfiguration = AppBarConfiguration(navController.graph, null)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
package dev.syorito_hatsuki.onlyone.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import coil.imageLoader
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging
import dev.syorito_hatsuki.onlyone.R
import dev.syorito_hatsuki.onlyone.api.OnlyOneApi
import dev.syorito_hatsuki.onlyone.databinding.ActivityMainBinding
import dev.syorito_hatsuki.onlyone.service.PushService
import dev.syorito_hatsuki.onlyone.ui.fragments.BlankFragmentDirections
import org.koin.java.KoinJavaComponent

val onlyOneApi by KoinJavaComponent.inject<OnlyOneApi>(OnlyOneApi::class.java)

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var pushBroadcastReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val string = intent.getStringExtra("token")
        if (string != null) {
            onlyOneApi.setToken(string)
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@addOnCompleteListener
            }
            val token = task.result
            /*lifecycleScope.launchWhenCreated {
                dev.syorito_hatsuki.onlyone.service.onlyOneApi.setFBMToken(token)
            }*/
            Log.e("TAG", "Token -> $token")
        }

        pushBroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val extras = intent?.extras
                extras?.keySet()?.firstOrNull {
                    it == PushService.KEY_ACTION
                }?.let { key ->
                    when (extras.getString(key)) {
                        PushService.ACTION_SHOW_MESSAGE -> {
                            extras.getString(PushService.KEY_MESSAGE)?.let { messageKey ->
                                Toast
                                    .makeText(
                                        applicationContext,
                                        messageKey,
                                        Toast.LENGTH_LONG
                                    ).show()
                            }
                        }
                        else -> Log.e("TAG", "no needed key found")
                    }
                }
            }
        }

        val intentFilter = IntentFilter()
        intentFilter.addAction(PushService.INTENT_FILTER)

        registerReceiver(pushBroadcastReceiver, intentFilter)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        lifecycleScope.launchWhenCreated {
            navView.menu.findItem(R.id.UserProfile).title = ""

            val avatar = intent.getStringExtra("avatar")
            val userid = intent.getIntExtra("id",0)
            val request = ImageRequest.Builder(applicationContext)
                .data("https://cdn.only-one.su/public/clients/${userid}/100-${avatar}")
                .transformations(CircleCropTransformation())
                .target{

                    it.setTintMode( PorterDuff.Mode.DST)

                    navView.menu.findItem(R.id.UserProfile).icon = it

                }
                .build()
            imageLoader.enqueue(request)
        }


        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment

        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.UserProfile
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    fun setTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        //Todo Make back func
        if (item.itemId == android.R.id.home) {
            val action = BlankFragmentDirections.goHome()

            val navController = findNavController(R.id.nav_host_fragment_activity_main)
            navController.navigate(action)
        }
        return true
    }

    override fun onDestroy() {
        unregisterReceiver(pushBroadcastReceiver)
        super.onDestroy()
    }

}
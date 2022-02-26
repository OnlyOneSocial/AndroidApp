package dev.syorito_hatsuki.onlyone.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavHostController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dev.syorito_hatsuki.onlyone.R
import dev.syorito_hatsuki.onlyone.databinding.ActivityMainBinding
import dev.syorito_hatsuki.onlyone.ui.fragments.BlankFragmentDirections

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var mToolBar: Toolbar;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //supportActionBar?.setDisplayHomeAsUpEnabled(false)

        //supportActionBar?.title = "Only One"

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        // TODO: MAKE CHANGE NAME of page
        menu.run {
            viewModel.changeTitle.observe(this@MainActivity) {
                title = it
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        //Todo Make back func
        if(item.itemId == android.R.id.home){
            val action = BlankFragmentDirections.goHome()


            //findNavController().navigate(action)
            //binding.findNavController().navigate(action)
            val navController = findNavController(R.id.nav_host_fragment_activity_main)
            navController.navigate(action)
        }

        return true
    }

     /*override fun OnCreateOptionsMenu(menu: Menu){
        super.onCreateOptionsMenu(menu)*/


//         override fun onClick()
        //menuInflater.inflate(R.menu.main_menu,menu)

        //return true


}
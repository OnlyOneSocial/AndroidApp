package dev.syorito_hatsuki.onlyone.ui.activity.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.syorito_hatsuki.onlyone.databinding.ActivityMainBinding
import dev.syorito_hatsuki.onlyone.ui.activity.main.fragment.main.view.MainFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, MainFragment.newInstance())
                .commitNow()
        }
    }
}
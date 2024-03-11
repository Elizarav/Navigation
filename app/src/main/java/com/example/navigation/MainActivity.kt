package com.example.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.navigation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.fragment_splash)
        Handler(Looper.myLooper()!!).postDelayed({
            setContentView(binding.root)
            binding.bottomNavigationMenu.setupWithNavController (
               navController = findNavController(R.id.navHostFragment)
            )
        }, 5000)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
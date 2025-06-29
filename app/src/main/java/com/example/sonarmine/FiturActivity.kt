package com.example.sonarmine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sonarmine.databinding.ActivityFiturBinding

class FiturActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFiturBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFiturBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}

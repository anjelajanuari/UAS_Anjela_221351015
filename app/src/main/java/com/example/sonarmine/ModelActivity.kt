package com.example.sonarmine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sonarmine.databinding.ActivityModelBinding

class ModelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityModelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModelBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}

package com.example.sonarmine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sonarmine.databinding.ActivityDatasetBinding

class DatasetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDatasetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDatasetBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}

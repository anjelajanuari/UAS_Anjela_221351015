package com.example.sonarmine

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sonarmine.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Navigasi ke halaman Tentang Aplikasi
        binding.menuTentangAplikasi.setOnClickListener {
            startActivity(Intent(this, AboutUsActivity::class.java))
        }

        // Navigasi ke halaman Dataset
        binding.menuDataset.setOnClickListener {
            startActivity(Intent(this, DatasetActivity::class.java))
        }

        // Navigasi ke halaman Fitur
        binding.menuFitur.setOnClickListener {
            startActivity(Intent(this, FiturActivity::class.java))
        }

        // Navigasi ke halaman Model
        binding.menuModel.setOnClickListener {
            startActivity(Intent(this, ModelActivity::class.java))
        }

        // Navigasi ke halaman Simulasi
        binding.menuSimulasi.setOnClickListener {
            startActivity(Intent(this, SimulasiActivity::class.java))
        }
    }
}

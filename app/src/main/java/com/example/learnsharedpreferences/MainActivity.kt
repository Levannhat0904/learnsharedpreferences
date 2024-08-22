package com.example.learnsharedpreferences

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableInt
import com.example.learnsharedpreferences.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val counter = ObservableInt(0)
    private lateinit var sharePref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setUpSharePref()
        loadSavedData()
        setUpListener()
//        setUpViews()
    }

    private fun setUpListener() {
        binding.btnAdd.setOnClickListener {
            counter.set(counter.get() + 1)
        }
        binding.btnMinus.setOnClickListener {
            counter.set(counter.get() - 1)
        }

    }

    private fun loadSavedData() {
        val oldCounter = sharePref.getInt(getString(R.string.saved_counter_key), 0)
        counter.set(oldCounter)
        binding.counter = counter

    }

    private fun setUpSharePref() {
        sharePref = getPreferences(Context.MODE_PRIVATE)
    }

    override fun onStop() {
        super.onStop()
        sharePref.edit().putInt(getString(R.string.saved_counter_key), counter.get()).apply()
    }
}
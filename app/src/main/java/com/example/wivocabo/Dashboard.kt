package com.example.wivocabo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wivocabo.databinding.ActivityDashboardBinding
import com.example.wivocabo.databinding.ActivityMainBinding

class Dashboard : AppCompatActivity() {
    private val TAG=Dashboard::class.java.simpleName
    private lateinit var binding:ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnadddevicemodal.setOnClickListener {
            AddNewDeviceForm().show(supportFragmentManager,"adddeviceform")
        }

    }
}
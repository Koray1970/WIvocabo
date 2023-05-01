package com.example.wivocabo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.wivocabo.database.AppDatabase
import com.example.wivocabo.database.Beacon
import com.example.wivocabo.database.BeaconsDao
import com.example.wivocabo.database.DeviceListAdapter
import com.example.wivocabo.databinding.ActivityDashboardBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Dashboard : AppCompatActivity() {
    private val TAG = Dashboard::class.java.simpleName
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var deviceListAdapter: DeviceListAdapter
    private val appDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "ivocabodb.db"
        ).fallbackToDestructiveMigration().build()
    }
    private lateinit var beaconsDao: BeaconsDao
    private lateinit var beaconlist: MutableList<Beacon>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnadddevicemodal.setOnClickListener {
            AddNewDeviceForm().show(supportFragmentManager, "adddeviceform")
        }

        beaconsDao = appDatabase.beaconsDao()
        binding.rvwdevicelist.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        CoroutineScope(Dispatchers.IO).launch {
            beaconlist = beaconsDao.beaconList()
            if (!beaconlist.isEmpty()) {
                deviceListAdapter= DeviceListAdapter(beaconlist, applicationContext)
                binding.rvwdevicelist.adapter =deviceListAdapter
                deviceListAdapter.notifyDataSetChanged()
            }
        }
    }

}
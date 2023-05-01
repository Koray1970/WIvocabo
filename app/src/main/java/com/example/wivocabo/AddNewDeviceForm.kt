package com.example.wivocabo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.wivocabo.database.AppDatabase
import com.example.wivocabo.database.Beacon
import com.example.wivocabo.database.BeaconsDao
import com.example.wivocabo.database.DeviceListAdapter
import com.example.wivocabo.database.EventResultFlag
import com.example.wivocabo.database.ParseEvents
import com.example.wivocabo.databinding.FragmentAddNewDeviceFormBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.parse.ParseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AddNewDeviceForm : BottomSheetDialogFragment() {
    private lateinit var binding:FragmentAddNewDeviceFormBinding
    private val appDatabase by lazy {
        activity?.let {
            Room.databaseBuilder(
                it.applicationContext,
                AppDatabase::class.java,
                "ivocabodb.db"
            ).fallbackToDestructiveMigration().build()
        }
    }
    private lateinit var beaconsDao: BeaconsDao
    private lateinit var beacon:Beacon
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity=requireActivity()
        binding.saveButton.setOnClickListener {
            if(validateFormElements()) {
                beaconsDao= appDatabase?.beaconsDao()!!
                var macaddress=binding.devicemacaddress.editText?.text.toString()
                var devicename=binding.devicename.editText?.text.toString()
                val parseEvents=ParseEvents()
                val parseresult=parseEvents.AddBeacon(activity.applicationContext,"0.0","0.0",macaddress,devicename,ParseUser.getCurrentUser().objectId)
                if(parseresult.eventResultFlag==EventResultFlag.SUCCESS) {
                    CoroutineScope(Dispatchers.IO).launch {
                        beacon = Beacon(0, 0.0, 0.0, macaddress, devicename, "0")
                        beaconsDao.insertBeacon(beacon)
                        val recycleview=Dashboard().findViewById<RecyclerView>(R.id.rvwdevicelist)
                        recycleview.adapter= DeviceListAdapter(beaconsDao.beaconList(),requireActivity().applicationContext)

                    }
                    this.dismiss()
                }
                else{
                    Toast.makeText(activity.applicationContext,"Something goes wrong!!",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding= FragmentAddNewDeviceFormBinding.inflate(inflater,container,false)
        return binding.root
    }
    fun validateFormElements():Boolean{

        if(binding.devicename.editText?.text.isNullOrEmpty() || binding.devicemacaddress.editText?.text.isNullOrEmpty()){
            if(binding.devicemacaddress.editText?.text.isNullOrEmpty()){
                binding.devicemacaddress.isErrorEnabled=true
                binding.devicemacaddress.error=getString(R.string.formelementisempty)
                return false
            }
            else{
                binding.devicemacaddress.isErrorEnabled=false
                binding.devicemacaddress.error=null
            }
            if(binding.devicename.editText?.text.isNullOrEmpty()){
                binding.devicename.isErrorEnabled=true
                binding.devicename.error=getString(R.string.formelementisempty)
                return false
            }
            else{
                binding.devicename.isErrorEnabled=false
                binding.devicename.error=null
            }
        }
        return true
    }
}
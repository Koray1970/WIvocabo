package com.example.wivocabo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wivocabo.databinding.FragmentAddNewDeviceFormBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AddNewDeviceForm : BottomSheetDialogFragment() {
    private lateinit var binding:FragmentAddNewDeviceFormBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity=requireActivity()
        binding.saveButton.setOnClickListener {
            if(validateFormElements()) {
                this.dismiss()
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
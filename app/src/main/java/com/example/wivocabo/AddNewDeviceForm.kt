package com.example.wivocabo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wivocabo.R
import com.example.wivocabo.databinding.FragmentAddNewDeviceFormBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AddNewDeviceForm : BottomSheetDialogFragment() {
    private lateinit var binding:FragmentAddNewDeviceFormBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity=requireActivity()
        binding.saveButton.setOnClickListener {
            this.dismiss()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding= FragmentAddNewDeviceFormBinding.inflate(inflater,container,false)
        return binding.root
    }
}
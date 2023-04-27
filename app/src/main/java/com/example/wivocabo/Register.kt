package com.example.wivocabo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class Register : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        frmusername=findViewById(R.id.txtusername)

        btnformsubmit=findViewById(R.id.btnsubmit)
        btnformsubmit.setOnClickListener {
            onRegisterFormSubmit()
        }
    }
    private fun onRegisterFormSubmit(){
        if(frmusername.editText!!.text.isEmpty()){
            frmusername.isErrorEnabled=true
            frmusername.error="Error"
        }
        else{
            frmusername.isErrorEnabled=false
            frmusername.error=null
        }
    }
    companion object{
        private lateinit var btnformsubmit:MaterialButton
        private lateinit var frmusername:TextInputLayout
    }
}
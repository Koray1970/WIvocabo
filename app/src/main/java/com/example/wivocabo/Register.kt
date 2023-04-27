package com.example.wivocabo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.wivocabo.database.EventResultFlag
import com.example.wivocabo.database.ParseEvents
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class Register : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        frmusername = findViewById(R.id.txtusername)
        frmemail = findViewById(R.id.txtemail)
        frmpassword = findViewById(R.id.txtpassword)
        listofelm = arrayListOf(frmusername, frmemail, frmpassword)
        btnformsubmit = findViewById(R.id.btnsubmit)
        btnformsubmit.setOnClickListener {
            onRegisterFormSubmit()
        }
    }

    private fun onRegisterFormSubmit() {
        if (validateisEmpty(listofelm).all { it == true }) {
            val parseEvent = ParseEvents()
            val dbresult = parseEvent.RegisterUser(
                applicationContext,
                frmusername.editText?.text.toString(),
                frmemail.editText?.text.toString(),
                frmpassword.editText?.text.toString()
            )
            if (dbresult.eventResultFlag == EventResultFlag.SUCCESS)
                Log.v(TAG, "OK")
        } else {
            Log.v(TAG, "FAILED")
        }

    }

    private fun validateisEmpty(elms: List<TextInputLayout>): BooleanArray {
        val result = ArrayList<Boolean>()
        var cont = 0;
        val strerror = resources.getString(R.string.formelementisempty)
        elms.forEach {
            if (it.editText?.text?.isEmpty() == true) {
                it.isErrorEnabled = true
                it.error = strerror
                result.add(false)
            } else {
                it.isErrorEnabled = false
                it.error = null
                result.add(true)
            }
            cont++
        }
        return result.toBooleanArray()
    }

    companion object {
        private val TAG = Register::class.java.simpleName
        private lateinit var btnformsubmit: MaterialButton
        private lateinit var frmusername: TextInputLayout
        private lateinit var frmemail: TextInputLayout
        private lateinit var frmpassword: TextInputLayout
        private lateinit var listofelm: ArrayList<TextInputLayout>
    }
}
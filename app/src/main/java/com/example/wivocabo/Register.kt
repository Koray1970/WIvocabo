package com.example.wivocabo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.coroutineScope
import androidx.room.Room
import com.example.wivocabo.database.AppDatabase
import com.example.wivocabo.database.EventResultFlag
import com.example.wivocabo.database.ParseEvents
import com.example.wivocabo.database.User
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.wait

class Register : AppCompatActivity() {
    private val appDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "ivocabodb.db"
        ).fallbackToDestructiveMigration().build()
    }

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
            _email = frmemail.editText?.text.toString()
            _password = frmpassword.editText?.text.toString()
            _username = frmusername.editText?.text.toString()
            val parseEvent = ParseEvents()
            val dbresult = parseEvent.RegisterUser(applicationContext, _username, _email, _password)

            if (dbresult.eventResultFlag == EventResultFlag.SUCCESS) {
                try {
                    _objectid = dbresult.resultGet() as String
                    val dbUserDao = appDatabase.userDao()
                    val user = User(0, _username, _email, _password, _objectid)
                    CoroutineScope(Dispatchers.IO).launch {
                        dbUserDao.insertUser(user)
                        val goToDashboard=Intent(this@Register,Dashboard::class.java)
                        startActivity(goToDashboard)
                    }

                } catch (exception: Exception) {
                    Log.v(TAG, "Exception ${exception.message}")
                }
            }

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

        private lateinit var _username: String
        private lateinit var _email: String
        private lateinit var _password: String
        private lateinit var _objectid: String
    }
}
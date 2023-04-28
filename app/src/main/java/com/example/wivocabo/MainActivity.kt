package com.example.wivocabo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.room.Room
import com.example.wivocabo.database.AppDatabase
import com.example.wivocabo.database.UserDao
import com.parse.ParseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var innerIntent:Intent

    private val appDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "ivocabodb.db"
        ).build()
    }
    private lateinit var dbUserDao:UserDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbUserDao=appDatabase.userDao()
        checkUserLogin()

    }
    private fun checkUserLogin(){
        CoroutineScope(Dispatchers.IO).launch {
            if(dbUserDao.countOfUser()>0) {
                if (ParseUser.getCurrentUser() == null) {
                    goToLoginForm()
                } else {
                    goToDashboard()
                }
            }
            else{
                goToRegisterForm()
            }
        }

    }
    private fun goToRegisterForm(){
        innerIntent= Intent(this,Register::class.java)
        startActivity(intent)
    }
    private fun goToLoginForm(){
        val intentloginform = Intent(this@MainActivity, Login::class.java)
        startActivity(intentloginform)
    }
    private fun goToDashboard(){
        val intentloginform = Intent(this@MainActivity, Dashboard::class.java)
        startActivity(intentloginform)
    }
}
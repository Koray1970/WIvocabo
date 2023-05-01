package com.example.wivocabo.database

import android.content.Context
import com.parse.ParseObject
import com.parse.ParseQuery
import android.content.res.Resources
import android.util.Log
import com.example.wivocabo.R
import com.parse.ParseUser
import com.parse.SignUpCallback
import kotlinx.coroutines.awaitAll
import okhttp3.internal.wait

class ParseEvents {
    private val TAG = ParseEvents::class.java.simpleName
    fun RegisterUser(
        context: Context,
        username: String,
        email: String,
        password: String
    ): ParseEventResult<String> {
        val parseEventResult = ParseEventResult<String>("")
        parseEventResult.eventResultFlag = EventResultFlag.FAILED
        try {
            if (ParseUser.getCurrentUser() != null)
                ParseUser.logOut()
            val user = ParseUser()
            user.setPassword(password)
            user.username = username
            user.email = email

            user.signUp()
            if (!user.objectId.isNullOrEmpty()) {
                Log.d(TAG, "Object saved.")
                parseEventResult.resultSet(user.objectId)
                parseEventResult.eventResultFlag = EventResultFlag.SUCCESS
            } else {
                ParseUser.logOut();
                Log.e(TAG, "Object not saved.")
            }
            /*user.signUpInBackground( SignUpCallback {
                if (it == null) {

                } else {

                }
            }).wait()*/
        } catch (exception: Exception) {
            parseEventResult.exception = exception.message.toString()
        }

        return parseEventResult
    }

    fun AddBeacon(
        context: Context,
        latitude: String,
        longitude: String,
        macaddress: String,
        devicename: String,
        parseuserid: String
    ):ParseEventResult<String> {
        val parseEventResult = ParseEventResult<String>("")
        parseEventResult.eventResultFlag = EventResultFlag.FAILED
        try{
            val parseObject=ParseObject("Beacons")
            parseObject.put("latitude",latitude)
            parseObject.put("mac",macaddress)
            parseObject.put("longitude",longitude)
            parseObject.put("parseUserId",parseuserid)
            parseObject.put("devicename",devicename)
            parseObject.save()
            if(parseObject.isDataAvailable("objectId")){
                parseEventResult.eventResultFlag=EventResultFlag.SUCCESS
                parseEventResult.resultSet(parseObject.objectId)
            }

        }
        catch (exception:Exception){
            parseEventResult.exception=exception.message.toString()
        }
        return parseEventResult
    }
}

class ParseEventResult<T>(private var iValue: T) {
    lateinit var eventResultFlag: EventResultFlag
    lateinit var errorcode: String
    lateinit var exception: String
    fun resultGet(): T {
        return iValue
    }

    fun resultSet(input: T) {
        iValue = input
    }
}

enum class EventResultFlag {
    SUCCESS, FAILED
}
package com.example.wivocabo.database

import android.content.Context
import com.parse.ParseObject
import com.parse.ParseQuery
import android.content.res.Resources
import com.example.wivocabo.R

class ParseEvents {
    fun RegisterUser(context:Context, username: String, email: String, password: String): ParseEventResult<String> {
        val dbuserclassname=  context.getString(R.string.pclass_user)
        val parseEventResult = ParseEventResult<String>("")
        parseEventResult.eventResultFlag = EventResultFlag.FAILED
        try {
            val userQuery = ParseQuery.getQuery<ParseObject>(dbuserclassname)
            var hasemail= userQuery.whereEqualTo("email", email)
            if(hasemail.count()>0){
                parseEventResult.errorcode = "PRS010"
                parseEventResult.resultSet(hasemail.first.objectId)
            }
            else{
                val user = ParseObject(dbuserclassname)
                user.put("username", username)
                user.put("email", email)
                user.put("password", password)
                user.save()
                parseEventResult.resultSet(user.objectId)
                parseEventResult.eventResultFlag = EventResultFlag.SUCCESS
            }

        } catch (exception: Exception) {
            parseEventResult.exception=exception.message.toString()
        }
        return parseEventResult
    }
}

class ParseEventResult<T>(private var iValue: T) {
    lateinit var eventResultFlag: EventResultFlag
    lateinit var errorcode: String
    lateinit var exception: String
    fun resultGet():T{
        return iValue
    }
    fun resultSet(input:T){
        iValue=input
    }
}

enum class EventResultFlag {
    SUCCESS, FAILED
}
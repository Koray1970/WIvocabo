package com.example.wivocabo.database

import com.parse.ParseObject
import com.parse.ParseQuery
import java.util.Objects
import java.util.UUID

class ParseEvents {
    fun RegisterUser(username: String, email: String, password: String): ParseEventResult<String> {
        val parseEventResult = ParseEventResult<String>()
        parseEventResult.eventResultFlag = EventResultFlag.FAILED
        try {
            val userQuery = ParseQuery<ParseObject>("user")
            userQuery.whereEqualTo("email", email)
            userQuery.countInBackground { count, e ->
                if (count > 0) {
                    parseEventResult.errorcode="PRS010"
                    parseEventResult.result= userQuery.first!!.get("objectId") as String
                }
                else{
                    val user = ParseObject("user")
                    user.put("username", username)
                    user.put("email", email)
                    user.put("password", password)
                    user.save()
                    userQuery.whereEqualTo("email", email)
                    parseEventResult.result= userQuery.first!!.get("objectId") as String
                    parseEventResult.eventResultFlag = EventResultFlag.SUCCESS
                }
            }
        } catch (exception: Exception) {

        }
        return parseEventResult
    }
}

class ParseEventResult<T> {
    lateinit var eventResultFlag: EventResultFlag
    lateinit var errorcode: String
    lateinit var exception: String
    var result: T = TODO()
}

enum class EventResultFlag {
    SUCCESS, FAILED
}
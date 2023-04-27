package com.example.wivocabo.database

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val uid:Int,
    @ColumnInfo(name="email") val email:String?,
    @ColumnInfo(name="password") val password:String?,
    @ColumnInfo(name="objectId") val objectId:String?
)

@Dao
interface UserDao{
    @Query("SELECT uid,email,password,objectId FROM users WHERE uid=:uid")
    fun getUser(uid: Int):User

    @Insert(onConflict=OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Update
    fun updateUser(vararg user: User)

    @Delete
    fun deleteUser(vararg user: User)
}

@Database(entities = [User::class], version = 1)
abstract class AppDatabase:RoomDatabase(){
    abstract fun userDao():UserDao
}


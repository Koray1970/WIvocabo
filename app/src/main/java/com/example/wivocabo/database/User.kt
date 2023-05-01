package com.example.wivocabo.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.Update

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "username") val username: String?,
    @ColumnInfo(name = "email") val email: String?,
    @ColumnInfo(name = "password") val password: String?,
    @ColumnInfo(name = "parceobjectId") val parceobjectid: String?
)
@Entity(tableName = "beacons")
data class Beacon(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "latitude") val latitude: Double?,
    @ColumnInfo(name = "longitude") val longitude: Double?,
    @ColumnInfo(name = "mac") val mac: String?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "parcebeaconId") val parcebeaconid: String?
)
@Entity(tableName = "missingbeacon")
data class MissingBeacon(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name="date") val date:Long?,
    @ColumnInfo(name = "latitude") val latitude: Double?,
    @ColumnInfo(name = "longitude") val longitude: Double?,
    @ColumnInfo(name = "mac") val mac: String?,
    @ColumnInfo(name = "devicename") val devicename: String?,
    @ColumnInfo(name = "parcebeaconId") val parcebeaconid: String?
)
@Entity(tableName = "trackarchive")
data class TrackArchive(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name="date") val date:Long?,
    @ColumnInfo(name = "latitude") val latitude: Double?,
    @ColumnInfo(name = "longitude") val longitude: Double?,
    @ColumnInfo(name = "mac") val mac: String?,
    @ColumnInfo(name = "parcebeaconId") val parcebeaconid: String?
)
@Entity(tableName = "missingarchive")
data class MissingArchive(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name="date") val date:Long?,
    @ColumnInfo(name = "latitude") val latitude: Double?,
    @ColumnInfo(name = "longitude") val longitude: Double?,
    @ColumnInfo(name = "mac") val mac: String?,
    @ColumnInfo(name = "parcebeaconId") val parcebeaconid: String?
)
@Dao
interface UserDao {
    @Query("SELECT id,email,password,parceobjectId FROM users WHERE email=:email")
    fun getUser(email:String): User

    @Query("SELECT COUNT(*) FROM users LIMIT 1")
    fun countOfUser(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Update
    fun updateUser(vararg user: User)

    @Delete
    fun deleteUser(vararg user: User)
}
@Dao
interface BeaconsDao {
    @Query("SELECT * FROM beacons WHERE mac=:mac")
    fun getBeacon(mac:String): Beacon

    @Query("SELECT * FROM beacons")
    fun beaconList(): MutableList<Beacon>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBeacon(beacon: Beacon)

    @Update
    fun updateBeacon(vararg beacon: Beacon)

    @Delete
    fun deleteBeacon(vararg beacon: Beacon)
}
@Dao
interface MissingBeaconDao {
    @Query("SELECT * FROM missingbeacon WHERE mac=:mac")
    fun getMissingBeacons(mac:String): MissingBeacon

    @Query("SELECT * FROM missingbeacon")
    fun missiongBeaconList(): MutableList<MissingBeacon>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMissingBeacon(missignBeacon: MissingBeacon)

    @Update
    fun updateMissingBeacon(vararg missignBeacon: MissingBeacon)

    @Delete
    fun deleteMissingBeacon(vararg missignBeacon: MissingBeacon)
}
@Dao
interface TrackArchiveDao {
    @Query("SELECT * FROM trackarchive WHERE mac=:mac")
    fun getTrackArchive(mac:String): TrackArchive

    @Query("SELECT * FROM trackarchive")
    fun trackArchiveList(): MutableList<TrackArchive>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrackArchive(trackArchive: TrackArchive)

    @Update
    fun updateTrackArchive(vararg trackArchive: TrackArchive)

    @Delete
    fun deleteTrackArchive(vararg trackArchive: TrackArchive)
}
@Database(entities = [User::class,Beacon::class,MissingBeacon::class, TrackArchive::class], version = 1)
@TypeConverters(DBConverts::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun beaconsDao():BeaconsDao
    abstract fun missingBeaconsDao():MissingBeaconDao
    abstract fun trackArchiveDao():TrackArchiveDao
}


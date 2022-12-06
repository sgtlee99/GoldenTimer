package com.example.goldentimer.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TimersDao {
    @Query("SELECT * FROM tb_timers")
    fun getAll() : List<Timers>

    @Insert
    fun insert(vararg timers : Timers)

    @Update
    fun update(timers : Timers)

    @Delete
    fun delete(timers : Timers)
}
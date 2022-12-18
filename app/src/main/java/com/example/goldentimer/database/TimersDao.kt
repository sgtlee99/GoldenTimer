package com.example.goldentimer.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TimersDao {

    @Query("SELECT * FROM tb_timers")
    fun getAll1() : List<Timers>

    @Query("SELECT * FROM tb_timers")
    fun getAll() : LiveData<List<Timers>>

    @Query("SELECT * FROM tb_timers WHERE id = :num")
    fun getById(num: Int) : Timers

    @Insert
    fun insert(vararg timers : Timers)

    @Update
    fun update(timers : Timers)

    @Delete
    fun delete(timers : Timers)

    @Query("DELETE FROM tb_timers WHERE id = :num")
    fun deleteById(num : Int)
}
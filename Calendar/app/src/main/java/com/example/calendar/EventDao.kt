package com.example.calendar

import androidx.lifecycle.LiveData
import androidx.room.*
import java.time.LocalDate

@Dao
interface EventDao {
    @Insert
    suspend fun insertEvent(event: EventForDB)

    @Delete
    suspend fun deleteEvent(event: EventForDB)

    @Update
    suspend fun updateEvent(event: EventForDB)

    @Query("SELECT * FROM EventsTable;")
    fun getAll(): LiveData<List<EventForDB>>

    @Query("DELETE FROM EventsTable;")
    suspend fun deleteAll()

}
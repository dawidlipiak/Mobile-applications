package com.example.calendar

import android.location.Location
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "EventsTable")
@TypeConverters(LocalDateConverter::class, LocalTimeConverter::class)
data class EventForDB(
    val title: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val location: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)
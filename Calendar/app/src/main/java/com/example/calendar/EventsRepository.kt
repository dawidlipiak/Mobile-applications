package com.example.calendar

import androidx.lifecycle.LiveData

class EventsRepository(private val eventDao: EventDao) {
    val readAllData: LiveData<List<EventForDB>> = eventDao.getAll()

    suspend fun insertEvent(event: EventForDB) {
        eventDao.insertEvent(event)
    }

    suspend fun updateEvent(event: EventForDB) {
        eventDao.updateEvent(event)
    }

    suspend fun deleteEvent(event: EventForDB) {
        eventDao.deleteEvent(event)
    }

    suspend fun deleteAllEvents() {
        eventDao.deleteAll()
    }

    fun getEvents(): List<EventForDB>? {
        return readAllData.value
    }
}
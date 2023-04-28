package com.example.calendar

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class EventModel(application: Application) : AndroidViewModel(application) {
    private val repository: EventsRepository
    val readAllData: LiveData<List<EventForDB>>

    init {
        val eventDao = EventsDatabase.getDatabase(application).eventDao()
        repository = EventsRepository(eventDao)
        readAllData = repository.readAllData
    }

    fun insertEvent(event: EventForDB) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertEvent(event)
        }
    }

    fun deleteEvent(event: EventForDB) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteEvent(event)
        }
    }

    fun updateEvent(event: EventForDB) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateEvent(event)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllEvents()
        }
    }

    fun getEventsNumber(date: LocalDate): Int {
        val events: List<EventForDB> = readAllData.value ?: return 0

        return events.filter { event -> event.startDate == date }.size
    }
}
package com.example.calendar

import android.util.Log
import java.time.*
import java.time.format.DateTimeFormatter

class CalendarUtils {
    companion object {
        var selectedDate: LocalDate = LocalDate.now()

        fun daysInMonthArray(): ArrayList<LocalDate> {
            val daysInMonthArray = ArrayList<LocalDate>()

            val yearMonth = YearMonth.from(selectedDate)
            val daysInMonth = yearMonth.lengthOfMonth()

            val prevMonth = selectedDate.minusMonths(1)
            val nextMonth = selectedDate.plusMonths(1)

            val prevYearMonth = YearMonth.from(prevMonth)
            val prevDaysInMonth = prevYearMonth.lengthOfMonth()

            val firstOfMonth = selectedDate.withDayOfMonth(1)
            val dayOfWeek = firstOfMonth.dayOfWeek.value
            for (i in 1..42) {
                if (i < dayOfWeek)
                    daysInMonthArray.add(LocalDate.of(prevMonth.year, prevMonth.month, prevDaysInMonth + i - dayOfWeek+1))
                else if (i >= daysInMonth + dayOfWeek)
                    daysInMonthArray.add(LocalDate.of(nextMonth.year, nextMonth.month, i - dayOfWeek - daysInMonth+1))
                else
                    daysInMonthArray.add(LocalDate.of(selectedDate.year, selectedDate.month, i - dayOfWeek+1))
            }
            return daysInMonthArray
        }

        fun daysInWeekArray(): ArrayList<LocalDate> {
            val days = ArrayList<LocalDate>()
            val sizeOfMonth = YearMonth.from(selectedDate).lengthOfMonth()
            for ( i in 1..sizeOfMonth) {
                days.add(LocalDate.of(selectedDate.year, selectedDate.month,i))
            }
            return days
        }
    }
}

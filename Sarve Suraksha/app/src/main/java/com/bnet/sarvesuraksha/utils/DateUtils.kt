package com.bnet.sarvesuraksha.utils

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateUtils {

    private val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    private val calendar: Calendar = Calendar.getInstance()

    /**
     * Formats the given date to "dd-MM-yyyy".
     */
    fun formatDate(date: Date): String {
        return dateFormat.format(date)
    }

    /**
     * Checks if the given date of birth is at least 18 years ago.
     */
    fun isAgeValid(dateOfBirth: Date): Boolean {
        val dobCalendar = Calendar.getInstance()
        dobCalendar.time = dateOfBirth

        val eighteenYearsAgo = Calendar.getInstance()
        eighteenYearsAgo.add(Calendar.YEAR, -18)

        return dobCalendar.before(eighteenYearsAgo)
    }

    /**
     * Gets the maximum date allowed for the DatePicker (18 years ago).
     */

    fun getMaxDateForDatePicker(): Long {
        val eighteenYearsAgo = Calendar.getInstance()
        eighteenYearsAgo.add(Calendar.YEAR, -18)
        return eighteenYearsAgo.timeInMillis
    }

    /**
     * Creates and returns a DatePickerDialog configured for age validation.
     */

    fun createDatePickerDialog(
        activity: AppCompatActivity,
        onDateSet: (Calendar) -> Unit
    ): DatePickerDialog {
        return DatePickerDialog(
            activity,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                val selectedDate = Calendar.getInstance().apply {
                    set(year, monthOfYear, dayOfMonth)
                }
                onDateSet(selectedDate)
            },
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        ).apply {
            datePicker.maxDate = getMaxDateForDatePicker()
        }
    }
}

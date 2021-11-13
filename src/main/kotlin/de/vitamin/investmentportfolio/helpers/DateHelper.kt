package de.vitamin.investmentportfolio.helpers

import java.text.SimpleDateFormat
import java.util.*

object DateHelper {
    fun getFormattedDate(date: String): String? {
        return try {
            val oldFormat = SimpleDateFormat("dd.mm.yyyy", Locale.ENGLISH)
            val newFormat = SimpleDateFormat("yyyy-mm-dd")
            val parsedDate = oldFormat.parse(date)
            val newDate = newFormat.format(parsedDate)
            newDate
        } catch (exception: Exception) {
            print(exception)
            null
        }
    }
}
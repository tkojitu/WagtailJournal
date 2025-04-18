package com.github.tkojitu.wagtailjournal

import java.io.File
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

class Musuko {
    var timestamp: Date? = null

    fun getFile(dir: File?): File {
        if (timestamp == null) updateTimestampByFile()
        val filename = dateFormat.format(timestamp)
        return File(dir, filename)
    }

    private val dateFormat: SimpleDateFormat
        get() = SimpleDateFormat("yyyyMMddHHmmss'.txt'")

    fun updateTimestampByFile() {
        timestamp = Date()
    }

    @Throws(ParseException::class)
    fun updateTimestampByFile(file: File?) {
        if (file == null) {
            updateTimestampByFile()
            return
        }
        val filename = file.name
        timestamp = parseDate(filename)
    }

    @Throws(ParseException::class)
    private fun parseDate(date: String): Date {
        return try {
            dateFormat.parse(date)
        } catch (e: ParseException) {
            SimpleDateFormat("yyyy-MM-dd'.txt'").parse(date)
        }
    }
}

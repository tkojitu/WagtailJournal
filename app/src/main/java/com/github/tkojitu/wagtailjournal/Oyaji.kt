package com.github.tkojitu.wagtailjournal

import android.widget.Toast
import java.io.File
import java.io.IOException
import java.text.ParseException

class Oyaji(
    private val activity: MainActivity,
    private val kakaa: Kakaa,
    private val musuko: Musuko,
    private val musume: Musume
) {
    private val directory = File("/storage/emulated/0/Documents/notes")

    private fun mkdirNote(): Boolean {
        if (directory.exists()) return true
        if (!directory.mkdir()) {
            Toast.makeText(activity, "cannot mkdir note", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    fun newJournal(text: String) {
        if (!saveJournal(text)) return
        musuko.updateTimestampByFile()
        activity.clear()
    }

    fun saveJournal(text: String): Boolean {
        if (text.isEmpty()) return false
        val file = musuko.getFile(directory)
        try {
            kakaa.save(file, text)
            return true
        } catch (e: IOException) {
            Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show()
            return false
        }
    }

    fun loadJournal() {
        if (!mkdirNote()) return
        try {
            val latest = musume.searchLatestJournal(directory)
            val text = kakaa.load(latest)
            musuko.updateTimestampByFile(latest)
            activity.update(text)
        } catch (e: IOException) {
            Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show()
        } catch (e: ParseException) {
            Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show()
        }
    }
}

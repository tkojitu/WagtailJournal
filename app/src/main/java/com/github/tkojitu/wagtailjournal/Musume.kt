package com.github.tkojitu.wagtailjournal

import java.io.File

class Musume {
    fun searchLatestJournal(directory: File): File? {
        var found: File? = null
        if (!directory.exists()) return null
        for (file in directory.listFiles()!!) {
            if (found == null) {
                found = file
                continue
            }
            if (file.lastModified() > found.lastModified()) {
                found = file
            }
        }
        return found
    }
}

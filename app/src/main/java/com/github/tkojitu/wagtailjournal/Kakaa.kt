package com.github.tkojitu.wagtailjournal

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException

class Kakaa {
    @Throws(IOException::class)
    fun save(file: File, text: String) {
        BufferedWriter(FileWriter(file)).use { bw ->
            bw.write(text)
        }
    }

    @Throws(IOException::class)
    fun load(file: File?): String {
        if (file == null) return ""
        BufferedReader(FileReader(file)).use { br ->
            val buf = StringBuilder()
            var ch: Int
            while ((br.read().also { ch = it }) >= 0) {
                buf.append(ch.toChar())
            }
            return buf.toString()
        }
    }
}

package com.github.tkojitu.wagtailjournal

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toUri

class MainActivity : AppCompatActivity() {
    private val container = Container()

    private val launcher = registerForActivityResult<Intent, ActivityResult>(
        StartActivityForResult(),
        object : ActivityResultCallback<ActivityResult> {
            override fun onActivityResult(result: ActivityResult) {
                if (result.resultCode != RESULT_OK) {
                    Toast.makeText(this@MainActivity, "permission denied", Toast.LENGTH_LONG).show()
                    return
                }
                val resultData = result.data
                if (resultData == null) {
                    Toast.makeText(this@MainActivity, "permission denied", Toast.LENGTH_LONG).show()
                    return
                }
                oyaji!!.loadJournal()
            }
        })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById<View>(R.id.toolbar) as Toolbar)
        setupContainer()
        requestPermission()
    }

    private fun requestPermission() {
        if (Environment.isExternalStorageManager()) {
            oyaji!!.loadJournal()
            return
        }
        val uri = ("package:" + BuildConfig.APPLICATION_ID).toUri()
        val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri)
        launcher.launch(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onPause() {
        super.onPause()
        oyaji!!.saveJournal(editText.text.toString())
    }

    private fun setupContainer() {
        val act = this
        container.defserv(
            "act",
            object : ServiceFactory {
                override fun create(co: Container?): Any {
                    return act
                }
            })
            .defserv(
                "oyaji",
                object : ServiceFactory {
                    override fun create(co: Container?): Any {
                        return Oyaji(
                            (co!!.geti("act") as MainActivity?)!!,
                            (co.geti("kakaa") as Kakaa?)!!,
                            (co.geti("musuko") as Musuko?)!!,
                            (co.geti("musume") as Musume?)!!
                        )
                    }
                })
            .defserv(
                "kakaa",
                object : ServiceFactory {
                    override fun create(co: Container?): Any {
                        return Kakaa()
                    }
                })
            .defserv(
                "musuko",
                object : ServiceFactory {
                    override fun create(co: Container?): Any {
                        return Musuko()
                    }
                })
            .defserv(
                "musume",
                object : ServiceFactory {
                    override fun create(co: Container?): Any {
                        return Musume()
                    }
                })
    }

    private val oyaji: Oyaji?
        get() = container.geti("oyaji") as Oyaji?

    private val editText: EditText
        get() = findViewById<View>(R.id.edit_text) as EditText

    fun onClickEnd(item: MenuItem?) {
        editText.setSelection(editText.text.length)
    }

    fun onClickHome(item: MenuItem?) {
        editText.setSelection(0)
    }
    fun onClickNew(item: MenuItem?) {
        oyaji!!.newJournal(editText.text.toString())
    }

    fun clear() {
        editText.text.clear()
    }

    fun update(text: String?) {
        editText.setText(text)
        editText.setSelection(editText.text.length)
    }
}

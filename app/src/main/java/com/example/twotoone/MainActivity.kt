package com.example.twotoone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var btnToPhraseGameActivity : Button
    private lateinit var btnToNumberGameActivity : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Games"
        btnToPhraseGameActivity = findViewById(R.id.btnToPhGame)
        btnToNumberGameActivity = findViewById(R.id.btnToNumGame)
        btnToPhraseGameActivity.setOnClickListener {
            val intent = Intent(this, PhraseGameActivity::class.java)
            startActivity(intent)
        }
        btnToNumberGameActivity.setOnClickListener {
            val intent2 = Intent(this, NumberGameActivity::class.java)
            startActivity(intent2)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val item : MenuItem = menu!!.getItem(2)
        item.isVisible = false
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.to_main -> {
                val intent = Intent(this, NumberGameActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.to_new -> {
                val intent = Intent(this, PhraseGameActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
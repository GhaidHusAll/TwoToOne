package com.example.twotoone

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class PhraseGameActivity : AppCompatActivity() {
    private lateinit var rvMain : RecyclerView
    private lateinit var btnGuess : Button
    private lateinit var userGuess : EditText
    private lateinit var tvMain : TextView
    private val words =
        arrayOf("Angry Elephant","Elephant Angry","Pinch Baby","Baby Pinch","Fish Reach","Reach Fish","Ball Flick","Flick Ball"
            ,"Remote Baseball","Baseball Remote","Football Roll","Roll Football","Basketball Fork","Fork Basketball","Sad Bounce","Bounce Sad","Giggle Scissors","Scissors Giggle")
    private var theWinningWord =""
    private var guesses = ArrayList<String>()
    private var guesses2 = ArrayList<Int>()
    private var tries = 0
    private var score = 0
    private var char = ArrayList<Char>()
    var  changedWord = ""
    var guessPhrase = true

    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.phrase_game_activity)
        supportActionBar?.title = "Phrase Game"

        sharedPreferences = this.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        score = sharedPreferences.getInt("highScore", 0)
        tries = 4
        char = arrayListOf()
        tvMain = findViewById(R.id.tvMainPhGame)
        rvMain = findViewById(R.id.rvMain)
        btnGuess = findViewById(R.id.btnGuess)
        userGuess = findViewById(R.id.etUserInput)
        rvMain.adapter = RVadapter(guesses,guesses2)
        rvMain.layoutManager = LinearLayoutManager(this)

        theWinningWord = words[Random.nextInt(0,words.size)]
        val replace = replaceString(theWinningWord)
        changedWord = replace

        tvMain.text =  replace
        btnGuess.setOnClickListener {
            if (tries > 0){
                checkTheUserInput(userGuess.text.toString())
                guesses.add("$tries guesses remaining")
                guesses2.add(2)
                rvMain.adapter?.notifyDataSetChanged()
                rvMain.scrollToPosition(guesses.size - 1)
                if (guessPhrase){
                    userGuess.hint = "Guess the full phrase"
                }else {
                    userGuess.hint = "Guess a letter"

                }
            }else {
                customAlert("there is no guesses remaining ")
            }
            userGuess.text.clear()

        }
    }

    private fun replaceString(word: String): String {
        println(word)
        val re = Regex("[^\\s.]")
        return re.replace(word, "★")

    }
    private fun checkTheUserInput(input: String) {
        if (guessPhrase){
            if (input.lowercase().equals(theWinningWord.lowercase(), true)) {
                // win / end game
                changedWord = theWinningWord
                tvMain.text = changedWord
                score++
                customAlert("you win the Game")
            }else {
                guesses.add("Wrong Guess $input")
                guesses2.add(0)
            }
            guessPhrase = false
        } else {
            if (input.length == 1){
                if (theWinningWord.contains(input.lowercase(), true)) {
                    val inputChar = input.toCharArray()[0]
                    val index = theWinningWord.lowercase().withIndex().filter { it.value == inputChar.lowercaseChar() }
                        .map { it.index }
                    char.add(inputChar.lowercaseChar())

                    for (j in index) {
                        changedWord = changedWord.replaceRange(j, j + 1, theWinningWord[j].toString())
                    }

                    tvMain.text = changedWord
                    guesses.add("Found ${index.size} ${input.uppercase()}(s)")
                    guesses2.add(1)
                    checkPhraseComplete()
                } else {
                    guesses.add("Wrong Guess $input")
                    guesses2.add(0)
                }
                guessPhrase = true
            }else{
                guesses.add("only Guess one letter $input")
                guesses2.add(0)
            }
        }
        tries--
    }
    private fun checkPhraseComplete(){
        if (!changedWord.contains("★", true)){
            //the phrase is completed
            score++
            customAlert("you win the Game")
        }
    }
    private fun endGame(){
        this.recreate()
        with(sharedPreferences.edit()) {
            putInt("highScore", score)
            apply()
        }
    }
    private fun customAlert(title:String){
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage(" the Highest Score is : $score \n do you want to play again ?:")
            .setPositiveButton("Yes", DialogInterface.OnClickListener {
                    _, _ ->  endGame()
            })
            .setNegativeButton("No", DialogInterface.OnClickListener {
                    dialog, _ -> dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle(title)
        alert.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val item : MenuItem = menu!!.getItem(0)
        item.title = "Back to Main Menu"
        val item2 : MenuItem = menu!!.getItem(1)
        item2.title = "to Number Game"
        return super.onPrepareOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.to_main -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.to_new -> {
                val intent = Intent(this, NumberGameActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.newGame -> {endGame()}
        }
        return super.onOptionsItemSelected(item)
    }
}
package app.emojitictactow

import android.content.Intent
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.TextView
import app.emojitictactow.database.DatabaseHandler
import app.emojitictactow.database.HighScoreModelClass
import java.util.*

class GameResults : AppCompatActivity() {

    override fun onBackPressed() {
        super.onBackPressed()

        goToMainMenu()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide()
        setContentView(R.layout.activity_game_results)

        val titleResults = findViewById(R.id.title_results) as TextView
        val saveScoreButton = findViewById(R.id.button_save_score) as Button
        val textField = findViewById(R.id.text_input_high_Score) as TextView
        val mainMenuButton = findViewById(R.id.button_Main_menu) as Button
        val playAgainButton = findViewById(R.id.button_play_again) as Button
        val results = intent.getStringExtra("results")
        val numberOfMoves = intent.getStringExtra("numberOfMoves")

        saveScoreButton.isEnabled = false
        textField.addTextChangedListener(object: TextWatcher {
            override fun onTextChanged(s:CharSequence, start:Int, before:Int, count:Int) {
                saveScoreButton.isEnabled = s.toString().trim{ it <= ' ' }.isNotEmpty()
            }
            override fun beforeTextChanged(s:CharSequence, start:Int, count:Int,
                                           after:Int) {
            }
            override fun afterTextChanged(s: Editable) {
            }
        })

        saveScoreButton.setOnClickListener {
            val highScoreDatabase = DatabaseHandler(this)
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())
            highScoreDatabase.addHighScore(HighScoreModelClass(
                textField.text.toString(),
                intent.getStringExtra("numberOfMoves")!!.toInt(),
                currentDate))
            println(currentDate)
            saveScoreButton.isEnabled = false

        }

        mainMenuButton.setOnClickListener {
            goToMainMenu()
        }

        playAgainButton.setOnClickListener {
            val ticTacTowGame = Intent(this, TicTacTowGame::class.java)
            val emojiPlayer1 = intent.getStringExtra("emojiPlayer1")
            val emojiPlayer2 = intent.getStringExtra("emojiPlayer2")
            val numberOfPlayers = intent.getStringExtra("numberOfPlayers")
            ticTacTowGame.putExtra("emojiPlayer1",emojiPlayer1)
            ticTacTowGame.putExtra("emojiPlayer2",emojiPlayer2)
            ticTacTowGame.putExtra("numberOfPlayers",numberOfPlayers)
            ticTacTowGame.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            ticTacTowGame.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(ticTacTowGame)
        }



        var resultMessage = ""

        when (results) {
            "win" -> {
                resultMessage = "You Win with $numberOfMoves moves"
                }
            "lose" -> {
                resultMessage = "You lose with $numberOfMoves moves"
            saveScoreButton.visibility = View.GONE
            textField.visibility = View.GONE
            }
            "playerOne" -> {
                resultMessage = "Player One wins with $numberOfMoves moves"
            }
            "playerTwo" -> {
                resultMessage = "Player Two wins with $numberOfMoves moves"
            }
            "tie" -> {
                resultMessage = "Tied with $numberOfMoves moves"
                saveScoreButton.visibility = View.GONE
                textField.visibility = View.GONE
            }
            else -> {
                println("Error")
            }
        }

        titleResults.text = resultMessage


    }

    fun goToMainMenu(){
        val home = Intent(this, MainActivity::class.java)

        home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(home)
    }


}
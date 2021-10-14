package app.emojitictactow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class TicTacTowGame : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide()
        setContentView(R.layout.activity_tic_tac_tow_game)

        val emojiPlayer1 = intent.getStringExtra("emojiPlayer1")
        val emojiPlayer2 = intent.getStringExtra("emojiPlayer2")
        println(emojiPlayer1)
        println(emojiPlayer2)


    }
}
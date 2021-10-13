package app.emojitictactow

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide()
        setContentView(R.layout.activity_main)


        val buttonPlayer1 = findViewById(R.id.button_players1) as Button
        buttonPlayer1.setOnClickListener {
            goToChooseEmoji("1")
        }
        val buttonPlayer2 = findViewById(R.id.button_players2) as Button
        buttonPlayer2.setOnClickListener {
            goToChooseEmoji("2")
        }


    }
    private fun goToChooseEmoji(numberOfPlayer: String){
        val chooseEmojiPlayer1 = Intent(this, ChooseEmojiPlayer1::class.java)
        chooseEmojiPlayer1.putExtra("numberOfPlayers", numberOfPlayer)
        startActivity(chooseEmojiPlayer1)
    }
}
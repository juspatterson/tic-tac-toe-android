package app.emojitictactow

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import app.emojitictactow.database.DatabaseHandler
import app.emojitictactow.database.HighScoreModelClass

class MainActivity : AppCompatActivity() {

    override fun onBackPressed() {
        super.onBackPressed()

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide()
        setContentView(R.layout.activity_main)



            //calling the viewEmployee method of DatabaseHandler class to read the records
//            val emp: List<HighScoreModelClass> = highScoreDatabase.viewHighScores()
//            val dateAndTime = Array<String>(emp.size){"0"}
//            val name = Array<String>(emp.size){"null"}
//            val numberOfMoves = Array<String>(emp.size){"null"}
//            var index = 0
//            for(e in emp){
//                dateAndTime[index] = e.dateAndTime
//                name[index] = e.name
//                numberOfMoves[index] = e.numberOfMoves.toString()
//                println("dongs $dateAndTime $name $numberOfMoves")
//                index++
//            }




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
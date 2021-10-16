package app.emojitictactow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import com.vanniktech.emoji.EmojiEditText
import com.vanniktech.emoji.EmojiPopup

class ChooseEmojiPlayer2 : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide()
        setContentView(R.layout.activity_choose_emoji_player2)

        val editText: EmojiEditText = findViewById(R.id.EmojiEditTextPLayer2)

        setTitle()
        setUpEmojiTextView(editText)
        startButton(editText)

    }

    private fun setTitle(){
        val numberOfPlayers = intent.getStringExtra("numberOfPlayers")

        if (numberOfPlayers.equals("1")){
            val titleChooseEmojiPlayer2 = findViewById(R.id.title_choose_emoji_player2) as TextView
            titleChooseEmojiPlayer2.text = "Choose emoji for\n Computer"
        }
    }

    private fun startButton(editText: EmojiEditText){
        val buttonStartGame = findViewById(R.id.button_start_game) as Button

        buttonStartGame.setOnClickListener {
            val ticTacTowGame = Intent(this, TicTacTowGame::class.java)
            ticTacTowGame.putExtra("emojiPlayer2", editText.text.toString())
            ticTacTowGame.putExtra("numberOfPlayers",intent.getStringExtra("numberOfPlayers"))
            ticTacTowGame.putExtra("emojiPlayer1", intent.getStringExtra("emojiPlayer1"))
            startActivity(ticTacTowGame)
        }

        buttonStartGame.isEnabled = false
        editText.addTextChangedListener(object: TextWatcher {
            override fun onTextChanged(s:CharSequence, start:Int, before:Int, count:Int) {
                buttonStartGame.isEnabled = s.toString().trim{ it <= ' ' }.isNotEmpty()
            }
            override fun beforeTextChanged(s:CharSequence, start:Int, count:Int,
                                           after:Int) {
            }
            override fun afterTextChanged(s: Editable) {
            }
        })
    }

    private fun setUpEmojiTextView(editText: EmojiEditText){

        var rootView: RelativeLayout = findViewById(R.id.root_view_choose_emoji_player2)

        val emojiPopup = EmojiPopup
            .Builder
            .fromRootView(rootView)
            .setKeyboardAnimationStyle(R.style.emoji_fade_animation_style)
            .build(editText)


        editText.forceSingleEmoji()

        editText.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus && !emojiPopup.isShowing){
                emojiPopup.show()
            }else {emojiPopup.dismiss()}
        }


        editText.setOnClickListener {
            emojiPopup.show()
        }
    }

}
package app.emojitictactow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RelativeLayout
import com.vanniktech.emoji.EmojiEditText
import com.vanniktech.emoji.EmojiPopup

class ChooseEmojiPlayer2 : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_emoji_player2)

        val numberOfPlayers = intent.getStringExtra("numberOfPlayers")
        println(numberOfPlayers)
        val test = intent.getStringExtra("emojiPlayer1")

        val editText: EmojiEditText = findViewById(R.id.EmojiEditTextPLayer2)

        setUpEmojiTextView(editText)

    }

    private fun startButton(editText: EmojiEditText){
        val buttonPlayer2 = findViewById(R.id.button_start_game) as Button
        buttonPlayer2.setOnClickListener {
            val chooseEmoji2 = Intent(this, ChooseEmojiPlayer1::class.java)
            chooseEmoji2.putExtra("emojiPlayer2", editText.text.toString())
            startActivity(chooseEmoji2)
        }
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
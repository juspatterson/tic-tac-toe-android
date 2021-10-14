package app.emojitictactow

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.vanniktech.emoji.EmojiEditText
import com.vanniktech.emoji.EmojiPopup


class ChooseEmojiPlayer1 : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide()
        setContentView(R.layout.activity_choose_emoji_player1)

        val editText: EmojiEditText = findViewById(R.id.EmojiEditTextPLayer1)

        setUpEmojiTextView(editText)
        nextButton(editText)

    }

    private fun nextButton(editText: EmojiEditText){
        val buttonPlayer2 = findViewById(R.id.button_next) as Button
        buttonPlayer2.setOnClickListener {
            val chooseEmojiPlayer2 = Intent(this, ChooseEmojiPlayer2::class.java)
            chooseEmojiPlayer2.putExtra("numberOfPlayers", intent.getStringExtra("numberOfPlayers"))
            chooseEmojiPlayer2.putExtra("emojiPlayer1", editText.text.toString())
            startActivity(chooseEmojiPlayer2)
        }
        buttonPlayer2.isEnabled = false
        editText.addTextChangedListener(object: TextWatcher {
            override fun onTextChanged(s:CharSequence, start:Int, before:Int, count:Int) {
                buttonPlayer2.isEnabled = s.toString().trim{ it <= ' ' }.isNotEmpty()
            }
            override fun beforeTextChanged(s:CharSequence, start:Int, count:Int,
                                           after:Int) {
            }
            override fun afterTextChanged(s: Editable) {
            }
        })
    }

    private fun setUpEmojiTextView(editText: EmojiEditText){

        var rootView: RelativeLayout = findViewById(R.id.main_activity_root_view)

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
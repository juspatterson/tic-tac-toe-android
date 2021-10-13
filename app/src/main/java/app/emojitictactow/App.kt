package app.emojitictactow

import android.app.Application
import com.vanniktech.emoji.EmojiManager
import com.vanniktech.emoji.ios.IosEmojiProvider


class App: Application() {
    override fun onCreate() {
        super.onCreate()
        EmojiManager.install(IosEmojiProvider())
        //Parse SDK stuff goes here
    }
}
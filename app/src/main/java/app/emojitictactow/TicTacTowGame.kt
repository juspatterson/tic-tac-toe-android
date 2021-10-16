package app.emojitictactow

import ItemsViewModel
import MyRecyclerViewAdapter
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class TicTacTowGame : AppCompatActivity() {

    override fun onBackPressed() {
        val alertdialog = AlertDialog.Builder(this)
        alertdialog.setTitle("End Game")
        alertdialog.setMessage("Are you sure you want to end the game")
        alertdialog.setPositiveButton("yes") { dialog, which ->
            val home = Intent(this, MainActivity::class.java)
            home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(home)
        }

        alertdialog.setNegativeButton(
            "No"
        ) { dialog, which -> dialog.cancel() }

        alertdialog.create()
        alertdialog.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide()
        setContentView(R.layout.activity_tic_tac_tow_game)

        val emojiPlayer1 = intent.getStringExtra("emojiPlayer1")
        val emojiPlayer2 = intent.getStringExtra("emojiPlayer2")
        val numberOfPlayers = intent.getStringExtra("numberOfPlayers")
        var gameLogic = GameLogic(numberOfPlayers!!,emojiPlayer1!!,emojiPlayer2!!,this)
        val recyclerview = findViewById(R.id.recyclerView) as RecyclerView

        // this creates a vertical layout Manager
        recyclerview.layoutManager = GridLayoutManager(this,resources.getInteger(R.integer.span))

        //for drawing the gird
        val drawable = GradientDrawable()
        drawable.orientation = GradientDrawable.Orientation.BOTTOM_TOP
        drawable.setColor(Color.parseColor("#17A41F"))
        drawable.setSize(25, 25)
        val dividerItemDecorator = DividerItemDecorator(drawable)
        recyclerview.addItemDecoration(dividerItemDecorator)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ItemsViewModel>()

        // This loop will create 9 views
        for (i in 1..9) {
            data.add(ItemsViewModel( ""))
        }

        // This will pass the ArrayList to our Adapter
        val adapter = MyRecyclerViewAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter

        //set height of recycle view
        var width = 0
        resources.displayMetrics.let { displayMetrics ->
            width = displayMetrics.widthPixels
        }
        val params = recyclerview.layoutParams
        params.height = width - 100
        recyclerview.layoutParams = params


        //get accesses to recycler view cell/row on click fun
        recyclerview.addOnItemTouchListener(
            RecyclerItemClickListener(this,
                recyclerview,
                object : RecyclerItemClickListener.OnItemClickListener {

            override fun onItemClick(view: View, position: Int) {
                val adapter = MyRecyclerViewAdapter(data)

                when(numberOfPlayers){
                    "1" -> {gameLogic.onePlayer(position,data,adapter,recyclerview)}
                    "2" -> {
                        gameLogic.twoPlayers(position,data,adapter,recyclerview)
                    }
                }
            }
            override fun onItemLongClick(view: View?, position: Int) { }
        }))

        val restartGameButton = findViewById(R.id.button_restart_game) as Button
        restartGameButton.setOnClickListener {
            gameLogic.restartGame(data,adapter,recyclerview,4)
        }

        //show alert for which player goose first
        gameLogic.selectPlayerOne(data,adapter,recyclerview,4)
    }

}
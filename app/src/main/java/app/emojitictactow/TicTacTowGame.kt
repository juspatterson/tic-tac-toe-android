package app.emojitictactow

import ItemsViewModel
import MyRecyclerViewAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TicTacTowGame : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide()
        setContentView(R.layout.activity_tic_tac_tow_game)

        val emojiPlayer1 = intent.getStringExtra("emojiPlayer1")
        val emojiPlayer2 = intent.getStringExtra("emojiPlayer2")
        println(emojiPlayer1)
        println(emojiPlayer2)

        val recyclerview = findViewById(R.id.recyclerView) as RecyclerView

        // this creates a vertical layout Manager
        recyclerview.layoutManager = GridLayoutManager(this,resources.getInteger(R.integer.span))

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ItemsViewModel>()

        // This loop will create 20 Views containing
        // the image with the count of view
        for (i in 1..20) {
            data.add(ItemsViewModel( "Item " + i))
        }

        // This will pass the ArrayList to our Adapter
        val adapter = MyRecyclerViewAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter

        recyclerview.addOnItemTouchListener(RecyclerItemClickListener(this, recyclerview, object : RecyclerItemClickListener.OnItemClickListener {

            override fun onItemClick(view: View, position: Int) {
                println(position)
                //do your work here..
            }
            override fun onItemLongClick(view: View?, position: Int) {
                TODO("do nothing")
            }
        }))


    }
}